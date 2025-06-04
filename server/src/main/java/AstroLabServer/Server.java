package AstroLabServer;

import AstroLab.auth.AuthResponse;
import AstroLab.auth.ConnectionType;
import AstroLab.auth.UserDTO;
import AstroLab.utils.ClientServer.ClientRequest;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.tcpProtocol.packet.ClientClosedConnectionException;
import AstroLab.utils.tcpProtocol.packet.PacketIsNullException;
import AstroLabServer.auth.AuthService;
import AstroLabServer.auth.AuthStates;
import AstroLabServer.auth.JwtUtils;
import AstroLabServer.database.DatabaseHandler;
import AstroLabServer.onlyServerCommand.OnlyServerResult;
import AstroLabServer.onlyServerCommand.ServerCommandManager;
import AstroLabServer.onlyServerCommand.ServerUtils;
import AstroLabServer.serverCommand.CommandManager;
import AstroLabServer.serverProtocol.ServerProtocol;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    public static final Logger LOGGER = LogManager.getLogger(Server.class);
    private static final int SELECTOR_TIMEOUT_MS = 1000;

    private final String serverHost;
    private final int serverPort;
    private boolean isRunning;

    private ServerUtils serverUtils;
    private Connection databaseConnection;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ExecutorService processPool;

    public Server(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
        this.isRunning = true;
        this.processPool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        initializeDependencies();
    }

    private void initializeServerChannel(ServerSocketChannel serverChannel, Selector selector) throws IOException {
        serverChannel.bind(new InetSocketAddress(serverHost, serverPort));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void run() {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            initializeServerChannel(serverChannel, selector);
            LOGGER.info("Server started on {}:{}", serverHost, serverPort);

            while (isRunning) {
                processConsoleCommands();
                if (selector.select(SELECTOR_TIMEOUT_MS) == 0) continue;

                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();

                    if (!key.isValid()) continue;
                    try {
                        if (key.isAcceptable()) {
                            handleAccept(key, selector);
                        } else if (key.isReadable()) {
                            handleRead(key, selector);
                        } else if (key.isWritable()) {
                            handleWrite(key);
                        }
                    } catch (Exception e) {
                        LOGGER.error("Key processing error: {}", e.getMessage());
                        key.cancel();
                        key.channel().close();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Server failure: {}", e.getMessage());
        } finally {
            shutdownServer();
        }
    }

    private void handleAccept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel client = serverChannel.accept();
        client.configureBlocking(false);

        ServerProtocol proto = new ServerProtocol(client);
        SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ);
        clientKey.attach(proto);

        LOGGER.info("Accepted connection from {}", client.getRemoteAddress());
    }

    private void handleRead(SelectionKey key, Selector selector) throws IOException {
        ServerProtocol proto = (ServerProtocol) key.attachment();
        SocketChannel client = (SocketChannel) key.channel();

        try {
            if (!proto.isAuthenticated()) {
                UserDTO dto = proto.receive(UserDTO.class);
                if (dto == null)
                    return;

                LOGGER.info("Handshake from {}: {}", client.getRemoteAddress(), dto);

                AuthService auth = serverUtils.getAuthService();
                AuthStates authStates = dto.getConnectionType() == ConnectionType.LOGIN ? auth.login(dto) : auth.register(dto);
                if (authStates.isState()) {
                    String token = JwtUtils.generateToken(dto.getLogin());
                    proto.setJwtToken(token);

                    AuthResponse authResponse = new AuthResponse(
                            ResponseStatus.OK,
                            authStates.getMessage(),
                            token
                    );

                    proto.send(authResponse);
                } else {
                    proto.send(new ServerResponse(ResponseStatus.FORBIDDEN, authStates.getMessage()));
                }
                key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                selector.wakeup();
                return;
            }

            ClientRequest clientRequest = proto.receive(ClientRequest.class);
            if (clientRequest == null) {
                return;
            }

            if (!JwtUtils.validateToken(clientRequest.getToken())) {
                proto.send(new ServerResponse(ResponseStatus.UNAUTHORIZED, "Invalid token"));
                key.interestOps(SelectionKey.OP_READ | key.interestOps() | SelectionKey.OP_WRITE);
                selector.wakeup();
                return;
            }

            String username = JwtUtils.getUsernameFromToken(clientRequest.getToken());
            LOGGER.info("Request from user {}: {}", username, clientRequest.toString());

            processPool.submit(() -> {
                ServerResponse resp = serverUtils.executeCommandSafely(clientRequest);
                proto.send(resp);
                key.interestOps(SelectionKey.OP_READ | key.interestOps() | SelectionKey.OP_WRITE);
                selector.wakeup();
            });

        } catch (PacketIsNullException | ClientClosedConnectionException e) {
            LOGGER.info("Client {} disconnected: {}", client.getRemoteAddress(), e.getMessage());
            key.cancel();
            client.close();
        } catch (SQLException e) {
            LOGGER.info("SQL Exception: {}", e.getMessage());
            key.cancel();
            client.close();
        }
    }

    private void handleWrite(SelectionKey key) throws IOException {
        ServerProtocol proto = (ServerProtocol) key.attachment();
        proto.flushWrites(key);
    }

    private void processConsoleCommands() {
        try {
            OnlyServerResult result = serverUtils.readConsoleCommand();
            switch (result) {
                case EXIT:
                    shutdownServer();
                    break;
                case EXCEPTION:
                    LOGGER.warn("Console command processing error");
                    break;
                case OK:
                    LOGGER.debug("Console command executed successfully");
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            LOGGER.error("Console command handler error: {}", e.getMessage());
        }
    }

    private void shutdownServer() {
        try {
            databaseConnection.close();
        } catch (Exception ignored) {}
        processPool.shutdown();
        isRunning = false;
    }

    private void initializeDependencies() {
        try {
            DatabaseHandler dbh = new DatabaseHandler();
            databaseConnection = dbh.connect();
            CommandManager clientCommandManager = new CommandManager(
                    dbh.read(databaseConnection), databaseConnection
            );
            AuthService auth = new AuthService(databaseConnection);
            serverUtils = new ServerUtils(
                    new ServerCommandManager(),
                    auth,
                    processPool,
                    clientCommandManager
            );
        } catch (Exception e) {
            LOGGER.fatal("Init error: {}", e.getMessage());
            System.exit(-1);
        }
    }

}