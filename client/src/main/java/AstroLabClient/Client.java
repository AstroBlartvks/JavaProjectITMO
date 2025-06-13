package AstroLabClient;

import AstroLab.actions.components.Action;
import AstroLab.auth.ConnectionType;
import AstroLab.auth.UserDTO;
import AstroLabClient.clientAction.Communicator;
import AstroLabClient.inputManager.*;

import java.rmi.ServerException;
import java.util.Scanner;

public final class Client implements Runnable{
    private final ScannerManager scannerManager;
    private CommandHandler commandHandler;
    private final String grpcServerHost;
    private final int grpcServerPort;
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 5000;
    private Communicator communicator;

    public Client(String host, int port) {
        grpcServerHost = host;
        grpcServerPort = port;
        this.scannerManager = new ScannerManager();
    }

    @Override
    public void run() {
        UserDTO userDTO = inputUserData();
        if (userDTO == null) {
            System.out.println("No user data provided. Exiting.");
            shutdown();
            return;
        }
        this.commandHandler = new CommandHandler(scannerManager, new ArgumentRequester(scannerManager), userDTO);

        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            boolean isOk = createCommunicatorAndRun(attempt, userDTO);
            if (isOk) break;
            if (attempt < MAX_RETRIES - 1) {
                System.out.println("Retrying connection in " + RETRY_DELAY_MS / 1000 + " seconds...");
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Retry delay interrupted.");
                    break;
                }
            } else {
                System.out.println("Max retries reached. Could not connect to server.");
            }
        }
        shutdown();
    }

    private boolean createCommunicatorAndRun(int attempt, UserDTO userDTO) {
        try {
            this.communicator = new Communicator(grpcServerHost, grpcServerPort, userDTO);

            while (scannerManager.hasNextLine()) {
                String inputString = input();
                if (inputString == null || inputString.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting client.");
                    return true;
                }
                Action action = commandHandler.handle(inputString);
                communicator.communicate(action);
            }
            return true;
        } catch (SecurityException e) {
            System.err.println("Authentication failed: " + e.getMessage());
            return true;
        } catch (ServerException e) {
            System.err.println("Server error: " + e.getMessage() + (attempt < MAX_RETRIES - 1 ? " Retrying..." : " Max retries reached."));
            return false;
        } catch (SystemInClosedException | ScannerException e) {
            System.err.println("Input error: " + e.getMessage() + ". Shutting down client.");
            return true;
        } catch (Exception e) {

            System.err.println("Unexpected Exception in client run loop (attempt " + (attempt+1) + "): " + e.getMessage());
            return false;
        } finally {
            if (this.communicator != null && attempt == MAX_RETRIES -1) {
                try {
                    this.communicator.shutdown();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    System.err.println("Client communicator shutdown interrupted.");
                }
            }
        }
    }

    public void shutdown() {
        if (communicator != null) {
            try {
                communicator.shutdown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Client communicator shutdown interrupted.");
            }
        }
        scannerManager.close();
        System.out.println("Client has closed!");
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

    private String input() {
        try {
            return scannerManager.readLine();
        } catch (IllegalStateException e) {
            System.out.println("Input error: " + e.getMessage());
            return null;
        }
    }
}