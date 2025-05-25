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
import java.util.concurrent.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The main server class for processing client connections and commands.
 * Uses non-blocking I/O using Selector and multithreaded query processing
 * using three thread pools:
 * - ForkJoinPool for reading requests
 * - FixedThreadPool for processing commands
 * - FixedThreadPool for sending responses
 */
public class Server {
    /** Logger for recording server events */
    public static final Logger LOGGER = LogManager.getLogger(Server.class );

    /** Timeout for events in the selector (in milliseconds) */
    private static final int SELECTOR_TIMEOUT_MS = 1000;

    /** Server Host */
    private final String serverHost;

    /** Server port */
    private final int serverPort;

    /** Server operation flag */
    private volatile boolean isRunning;

    /** Server utilities for command processing and authentication */
    private ServerUtils serverUtils;

    /** JSON mapper for data serialization/deserialization */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /** Client Team Manager */
    private CommandManager clientCommandManager;

    /** Database Connection */
    private Connection databaseConnection;

    /** Thread pool for reading incoming requests */
    private final ForkJoinPool readPool;

    /** Thread pool for processing requests */
    private final ExecutorService processPool;

    /** Thread pool for sending responses */
    private final ExecutorService sendPool;

    /**
     * Server Constructor
     * @param host The IP address or hostname of the server
     * @param port Listening port
     */
    public Server(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
        this.isRunning = true;
        this.readPool = new ForkJoinPool();
        this.processPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.sendPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        initializeDependencies();
    }

    /**
     * Initializing the server channel and registering it in the selector
     * @param serverChannel Server Channel
     * @param selector is a selector for event handling
     * @throws IOException On input/output errors
     */
    private void initializeServerChannel(ServerSocketChannel serverChannel,Selector selector) throws IOException{
        try {
            serverChannel.bind(new InetSocketAddress(serverHost, serverPort));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IllegalArgumentException | SecurityException | AlreadyBoundException e) {
            LOGGER.fatal("You can't bind server{ip={}, port={}}, because: {}",
                    this.serverHost, this.serverPort, e.getMessage());
        } catch (ClosedChannelException | ClosedSelectorException e) {
            LOGGER.fatal("Your server's channel or selector closed! {}", e.getMessage());
        }
    }

    /**
     * Main server startup method
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
            shutdownServer();
            LOGGER.info("Server shutdown sequence completed");
        }
    }

    /**
     * Processing network events via the selector
     * @param selector is a selector for event monitoring
     * @throws IOException On input/output errors
     */
    private void processNetworkEvents(Selector selector) throws IOException {
        if (selector.select(SELECTOR_TIMEOUT_MS) == 0) return;

        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            keyIterator.remove();

            if (key.isAcceptable()) {
                serverUtils.handleNewConnection(key, selector);
            } else if (key.isReadable()) {
                handleReadableKey(key);
            }
        }
    }

    /**
     * Processing the data read event from the channel
     * @param key Selector key with a readable channel
     */
    private void handleReadableKey(SelectionKey key) {
        readPool.submit(() -> {
            SocketChannel clientChannel = (SocketChannel) key.channel();
            try {
                key.interestOps(0);

                ServerResponse response = processClientRequest(key);

                processPool.submit(() -> {
                    sendPool.submit(() -> {
                        try {
                            serverUtils.handleSendResponse(clientChannel, response);
                        } finally {
                            if (key.isValid()) {
                                key.interestOps(SelectionKey.OP_READ);
                                key.selector().wakeup();
                            }
                        }
                    });
                });
            } catch (Exception e) {
                LOGGER.error("Processing error: {}", e.getMessage());
                serverUtils.closeChannel(key);
            }
        });
    }

    /**
     * Client request processing
     * @param key Selector key with client channel
     * @return Server response
     * @throws IOException On read/write errors
     * @throws SQLException For DATABASE errors
     */
    private ServerResponse processClientRequest(SelectionKey key) throws IOException, SQLException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ClientRequest request = serverUtils.readClientRequest(key, clientChannel, ClientRequest.class);

        if (request == null) {
            return new ServerResponse(ResponseStatus.EXCEPTION, "Your request is empty");
        }

        UserDTO userDTO = new UserDTO(
                request.getRequest().getOwnerLogin(),
                request.getRequest().getOwnerPassword(),
                "", ConnectionType.LOGIN);
        if (!serverUtils.loginUser(userDTO)) {
            return new ServerResponse(ResponseStatus.EXCEPTION, "You are not logged!");
        }

        return executeCommandSafely(request.getRequest());
    }

    /**
     * Correct shutdown of the server
     */
    private void shutdownServer() {
        try {
            databaseConnection.close();
            readPool.shutdown();
            processPool.shutdown();
            sendPool.shutdown();
            if (!readPool.awaitTermination(5, TimeUnit.SECONDS)) readPool.shutdownNow();
            if (!processPool.awaitTermination(5, TimeUnit.SECONDS)) processPool.shutdownNow();
            if (!sendPool.awaitTermination(5, TimeUnit.SECONDS)) sendPool.shutdownNow();
            LOGGER.info("Server shutdown initiated");
        } catch (Exception e) {
            LOGGER.error("Error during shutdown: {}", e.getMessage());
        } finally {
            isRunning = false;
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
    private void initializeDependencies() {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {
            ServerCommandManager serverCommandManager = new ServerCommandManager();
            DatabaseHandler databaseHandler = new DatabaseHandler();

            try {
                databaseConnection = databaseHandler.connect();
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