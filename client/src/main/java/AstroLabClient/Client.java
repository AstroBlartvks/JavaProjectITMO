package AstroLabClient;

import AstroLab.actions.components.Action;
import AstroLabClient.ClientProtocol.Communicator;
import AstroLabClient.inputManager.*;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.ServerException;

public final class Client {
    private final ScannerManager scannerManager;
    private final CommandHandler commandHandler;
    private final String serverHost;
    private final int serverPort;
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 5000;

    public Client(String host, int port) {
        serverHost = host;
        serverPort = port;
        this.scannerManager = new ScannerManager();
        this.commandHandler = new CommandHandler(scannerManager, new ArgumentRequester(scannerManager));
    }

    public void run() {
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            boolean isOk = createSocketAndRun(attempt);
            if (isOk) break;
        }
        shutdown();
    }

    private boolean createSocketAndRun(int attempt) {
        try (Socket socket = new Socket()) {
            Communicator communicator = new Communicator(socket, serverHost, serverPort);

            while (scannerManager.hasNextLine()) {
                String inputString = input();
                if (inputString == null) continue;
                Action action = commandHandler.handle(inputString);

                communicator.communicate(action);
            }
        } catch (SocketException e) {
            handleRetry(attempt, e);
        } catch (ServerException e) {
            return true;
        } catch (SystemInClosedException | ScannerException e) {
            shutdown();
        } catch (Exception e) {
            System.out.println("Unexpected Exception: " + e.getMessage());
        }
        return false;
    }

    private void handleRetry(int attempt, Exception e) {
        System.out.println("Attempt " + (attempt + 1) + " failed: " + e.getMessage());
        if (attempt < MAX_RETRIES - 1) {
            try {
                Thread.sleep(RETRY_DELAY_MS);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void shutdown() {
        scannerManager.close();
        System.out.println("Client has closed!");
    }

    private String input() {
        try {
            return scannerManager.readLine();
        } catch (IllegalStateException e) {
            System.out.println("Input error: " + e.getMessage());
            return null;
        }
    }
}