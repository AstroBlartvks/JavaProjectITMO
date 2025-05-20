package AstroLabServer;

import AstroLab.actions.components.Action;
import AstroLab.auth.ConnectionType;
import AstroLab.auth.UserDTO;
import AstroLab.utils.ClientServer.ClientRequest;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLabServer.auth.AuthService;
import AstroLabServer.database.DatabaseHandler;
import AstroLabServer.onlyServerCommand.OnlyServerResult;
import AstroLabServer.onlyServerCommand.ServerCommandManager;
import AstroLabServer.onlyServerCommand.ServerUtils;
import AstroLabServer.serverCommand.CommandManager;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.security.sasl.AuthenticationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The main server class for processing client connections and commands
 */
public class Server {
    public static final Logger LOGGER = LogManager.getLogger(Server.class);
    private static final int SELECTOR_TIMEOUT_MS = 1000;

    // Server config
    private final String serverHost;
    private final int serverPort;
    private volatile boolean isRunning;

    private ServerUtils serverUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private CommandManager clientCommandManager;
    private Connection databaseConnection;

    public Server(String host, int port, String dbHost) {
        this.serverHost = host;
        this.serverPort = port;
        this.isRunning = true;
        initializeDependencies(dbHost);
    }

    /**
     * Main of server
     */
    public void run() {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            initializeServerChannel(serverChannel, selector);
            LOGGER.info("Server started successfully on {}:{}", serverHost, serverPort);

            while (isRunning) {
                processConsoleCommands();
                processNetworkEvents(selector);
            }

        } catch (Exception e) {
            LOGGER.error("Critical server error: {}", e.getMessage());
        } finally {
            LOGGER.info("Server shutdown sequence completed");
        }
    }

    /**
     * Initialization of the server channel and registration in the selector
     */
    private void initializeServerChannel(ServerSocketChannel channel, Selector selector) throws IOException {
        try {
            channel.bind(new InetSocketAddress(serverHost, serverPort));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IllegalArgumentException e) {
            logFatalError("Invalid address/port configuration", e);
        } catch (SecurityException | AlreadyBoundException e) {
            logFatalError("Binding permission issue", e);
        } catch (ClosedChannelException e) {
            logFatalError("Channel closed during initialization", e);
        }
    }

    /**
     * Processing network events via a selector
     */
    private void processNetworkEvents(Selector selector) throws IOException {
        if (selector.select(SELECTOR_TIMEOUT_MS) == 0) return;

        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            keyIterator.remove();

            try {
                if (key.isAcceptable()) {
                    serverUtils.handleNewConnection(key, selector);
                } else if (key.isReadable()) {
                    processClientRequest(key);
                }
            } catch (AuthenticationException e) {
                LOGGER.info("AuthenticationException: {}. Close connection", e.getMessage());
            } catch (IOException e) {
                LOGGER.error("Network error: {}", e.getMessage());
                serverUtils.closeChannel(key);
            } catch (SQLException e) {
                LOGGER.error("SQL Exception: {}", e.getMessage());
            }
        }
    }

    /**
     * Processing a request from a client
     */
    private void processClientRequest(SelectionKey key) throws IOException, SQLException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ClientRequest request = serverUtils.readClientRequest(key, clientChannel, ClientRequest.class);

        if (request != null) {
            UserDTO userDTO = new UserDTO(  request.getRequest().getOwnerLogin(),
                                            request.getRequest().getOwnerPassword(),
                                            "", ConnectionType.LOGIN);
            if (!serverUtils.loginUser(clientChannel, userDTO)){
                serverUtils.handleSendResponse(clientChannel, new ServerResponse(ResponseStatus.EXCEPTION, "You are not logged!"));
            }
            ServerResponse response = executeCommandSafely(request.getRequest());
            serverUtils.handleSendResponse(clientChannel, response);
        }
    }

    /**
     * Processing of server console commands
     */
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

    /**
     * Secure execution of a client command with exception handling
     */
    private ServerResponse executeCommandSafely(Action command) {
        try {
            return clientCommandManager.executeCommand(command);
        } catch (Exception e) {
            LOGGER.error("Command execution error: {}", e.getMessage());
            return new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
    }

    /**
     * Initialization of server dependencies
     */
    private void initializeDependencies(String dbHost) {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {
            ServerCommandManager serverCommandManager = new ServerCommandManager();
            DatabaseHandler databaseHandler = new DatabaseHandler();

            try {
                databaseConnection = databaseHandler.connect(dbHost);
                clientCommandManager = new CommandManager(databaseHandler.read(databaseConnection), databaseConnection);
            } catch (ClassNotFoundException e) {
                logInitializationError("Driver of PostgreSQL isn't exist", e);
            } catch (SQLException e) {
                logInitializationError("Error while trying to connect/read to DB", e);
            }

            AuthService authService = new AuthService(databaseConnection);
            serverUtils = new ServerUtils(serverCommandManager, authService);

        } catch (JsonMappingException e) {
            logInitializationError("JSON parsing error", e);
        } catch (Exception e) {
            logInitializationError("General initialization error", e);
        }
    }

    /**
     * Correct shutdown of the server
     */
    private void shutdownServer() {
        try {
            databaseConnection.close();
            LOGGER.info("Server shutdown initiated");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            isRunning = false;
        }
    }

    private void logFatalError(String context, Exception e) {
        LOGGER.fatal("{} [Host: {}, Port: {}]: {}",
                context, serverHost, serverPort, e.getMessage());
        System.exit(-1);
    }

    private void logInitializationError(String context, Exception e) {
        LOGGER.error("{}: {}", context, e.getMessage());
        LOGGER.debug("Stack trace:", e);
        System.exit(-1);
    }
}