package AstroLabServer;

import AstroLabServer.auth.AuthService;
import AstroLabServer.database.DatabaseHandler;
import AstroLabServer.onlyServerCommand.OnlyServerResult;
import AstroLabServer.onlyServerCommand.ServerCommandManager;
import AstroLabServer.onlyServerCommand.ServerUtils;
import AstroLabServer.serverCommand.CommandManager;

import java.io.IOException;
import java.sql.Connection;
import java.util.concurrent.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import AstroLabServer.grpc.AstroAuthServiceImpl;
import AstroLabServer.grpc.AstroCommandServiceImpl;
import io.grpc.ServerBuilder;
import java.util.concurrent.TimeUnit;


public class Server {
    public static final Logger LOGGER = LogManager.getLogger(Server.class);

    private final int grpcPort;
    private boolean isRunning;
    private io.grpc.Server grpcServer;

    private ServerUtils serverUtils;
    private Connection databaseConnection;
    private final ExecutorService processPool;

    public Server(int port, DatabaseHandler databaseHandler) {
        this.grpcPort = port;
        this.isRunning = true;
        this.processPool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        initializeDependencies(databaseHandler);
    }

    public void run() {
        AuthService authService = serverUtils.authService();

        grpcServer = ServerBuilder.forPort(grpcPort)
                .addService(new AstroAuthServiceImpl(authService))
                .addService(new AstroCommandServiceImpl(serverUtils))
                .executor(processPool)
                .build();
        try {
            grpcServer.start();
            LOGGER.info("gRPC Server started on port {}", grpcPort);

            Thread consoleThread = getConsoleThread();
            consoleThread.start();

            grpcServer.awaitTermination();
        } catch (IOException e) {
            LOGGER.error("gRPC server startup failed: {}", e.getMessage());
        } catch (InterruptedException e) {
            LOGGER.error("gRPC server awaitTermination interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            shutdownServer();
        }
    }

    private Thread getConsoleThread() {
        Thread consoleThread = new Thread(() -> {
            while (isRunning) {
                try {
                    processConsoleCommands();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    LOGGER.info("Console processing interrupted.");
                    break;
                }
            }
        });
        consoleThread.setDaemon(true);
        return consoleThread;
    }

    private void processConsoleCommands() {
        try {
            OnlyServerResult result = serverUtils.readConsoleCommand();
            switch (result) {
                case EXIT:
                    LOGGER.info("Exit command received. Shutting down server...");
                    isRunning = false;
                    if (grpcServer != null) {
                        grpcServer.shutdown();
                    }
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
        LOGGER.info("Shutting down gRPC server...");
        if (grpcServer != null && !grpcServer.isTerminated()) {
            try {
                grpcServer.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOGGER.warn("gRPC server shutdown interrupted.", e);
                grpcServer.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        try {
            if (databaseConnection != null && !databaseConnection.isClosed()) {
                databaseConnection.close();
                LOGGER.info("Database connection closed.");
            }
        } catch (Exception e) {
            LOGGER.warn("Error closing database connection: {}", e.getMessage());
        }

        if (processPool != null && !processPool.isShutdown()) {
            processPool.shutdown();
            try {
                if (!processPool.awaitTermination(5, TimeUnit.SECONDS)) {
                    processPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                processPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
            LOGGER.info("Process pool shut down.");
        }
        isRunning = false;
        LOGGER.info("Server shutdown complete.");
    }

    private void initializeDependencies(DatabaseHandler databaseHandler) {
        try {
            databaseConnection = databaseHandler.connect();
            CommandManager clientCommandManager = new CommandManager(
                    databaseHandler.read(databaseConnection), databaseConnection
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