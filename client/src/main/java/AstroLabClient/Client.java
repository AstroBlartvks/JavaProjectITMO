package AstroLabClient;

import AstroLab.actions.components.Action;
import AstroLab.auth.ConnectionType;
import AstroLab.auth.UserDTO;
import AstroLabClient.ClientProtocol.Communicator;
import AstroLabClient.inputManager.*;

import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.ServerException;
import java.util.Scanner;

public final class Client {
    private final ScannerManager scannerManager;
    private CommandHandler commandHandler;
    private final String serverHost;
    private final int serverPort;
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 3000;

    public Client(String host, int port) {
        serverHost = host;
        serverPort = port;
        this.scannerManager = new ScannerManager();
    }

    public void run() {
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            UserDTO userDTO = inputUserData();
            this.commandHandler = new CommandHandler(scannerManager, new ArgumentRequester(scannerManager), userDTO);
            if (userDTO == null) continue;
            boolean isOk = createSocketAndRun(attempt, userDTO);
            if (isOk) break;
        }
        shutdown();
    }

    private boolean createSocketAndRun(int attempt, UserDTO userDTO) {
        try (Socket socket = new Socket()) {
            Communicator communicator = new Communicator(socket, serverHost, serverPort, userDTO);

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

    private UserDTO inputUserData(){
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Enter the type of operation (Login - 1, Registration - 2): ");
        if (!inputScanner.hasNextLine()){
            System.out.println("Incorrect input, try again!");
            return null;
        }

        String type = inputScanner.next();
        ConnectionType connectionType;

        switch (type){
            case "1":
                connectionType = ConnectionType.LOGIN;
                break;
            case "2":
                connectionType = ConnectionType.REGISTER;
                break;
            default:
                System.out.println("Incorrect input, try again!");
                return null;
        }
        return createRequestToLoginOrRegister(connectionType);
    }

    private UserDTO createRequestToLoginOrRegister(ConnectionType connectionType){
        Scanner loginInputScanner = new Scanner(System.in);
        System.out.print("Enter the login: ");
        String login = loginInputScanner.next();
        System.out.print("Enter the password: ");
        String password = loginInputScanner.next();
        return new UserDTO(login, password, "", connectionType);
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