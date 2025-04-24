package org.javaLab6.Client;

import org.javaLab6.Client.inputManager.*;
import org.javaLab6.Client.utils.ClientServer.ClientRequest;
import org.javaLab6.Client.utils.ClientServer.ClientStatus;
import org.javaLab6.Client.utils.ClientServer.ResponseStatus;
import org.javaLab6.Client.utils.ClientServer.ServerResponse;
import org.javaLab6.Client.utils.command.CommandArgumentList;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public final class Client {
    private final ScannerManager scannerManager;
    private final CommandHandler commandHandler;
    private final String serverIp;
    private final int serverPort;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 5000;
    private static final int BUFFER_SIZE_BYTES = 1024;
    private boolean isRunning = true;

    public Client(String serverIp, int serverPort) {
        this.scannerManager = new ScannerManager();
        this.commandHandler = new CommandHandler(scannerManager, new ArgumentRequester(scannerManager));
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void run() {
        while (isRunning && scannerManager.hasNextLine()) {
            String inputString = input();
            if (inputString == null) continue;

            CommandArgumentList commandArgList = handleCommand(inputString);
            if (commandArgList == null) continue;

            ClientRequest request = new ClientRequest(ClientStatus.REQUEST, commandArgList.getArgList());
            communicateWithServer(request);
        }
        shutdown();
    }

    private void communicateWithServer(ClientRequest request) {
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(serverIp, serverPort), RETRY_DELAY_MS);
                sendRequest(socket, request);
                ServerResponse response = receiveResponse(socket);

                if (response.getStatus() == ResponseStatus.EXIT){
                    isRunning = false;
                }
                else if (response.getStatus() == ResponseStatus.OK || response.getStatus() == ResponseStatus.TEXT || response.getStatus() == ResponseStatus.DATA) {
                    System.out.println(response.getValue());
                } else if (response.getStatus() == ResponseStatus.EXCEPTION){
                    System.err.println("Server exception: " + response);
                }else {
                    System.out.println("Server response: " + response);
                }
                return;
            } catch (ConnectException | SocketTimeoutException e) {
                handleRetry(attempt, e);
            } catch (IOException e) {
                System.out.println("Communication error: " + e.getMessage());
                return;
            }
        }
        System.out.println("Server unavailable after " + MAX_RETRIES + " attempts.");
    }

    private void sendRequest(Socket socket, ClientRequest request) throws IOException {
        OutputStream out = socket.getOutputStream();
        String jsonRequest = objectMapper.writeValueAsString(request);
        out.write(jsonRequest.getBytes(StandardCharsets.UTF_8));
        out.flush();
    }

    private ServerResponse receiveResponse(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder responseBuilder = new StringBuilder();
        char[] buffer = new char[BUFFER_SIZE_BYTES];
        int status;

        while ((status = in.read(buffer)) > 0) {
            responseBuilder.append(buffer);
            if (status < BUFFER_SIZE_BYTES){
                break;
            }
        }
        return objectMapper.readValue(responseBuilder.toString(), ServerResponse.class);
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

    private CommandArgumentList handleCommand(String input){
        try {
            return commandHandler.handle(input);
        } catch (SystemInClosedException | ScannerException e) {
            shutdown();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}