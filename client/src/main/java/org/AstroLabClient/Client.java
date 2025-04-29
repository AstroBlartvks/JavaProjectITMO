package org.AstroLabClient;

import org.AstroLab.utils.tcpProtocol.packet.Packet;
import org.AstroLabClient.ClientProtocol.ClientProtocol;
import org.AstroLabClient.inputManager.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.AstroLab.utils.ClientServer.ClientRequest;
import org.AstroLab.utils.ClientServer.ClientStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public final class Client {
    private final ScannerManager scannerManager;
    private final CommandHandler commandHandler;
    private final String serverHost;
    private final int serverPort;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 5000;
    private static final int BUFFER_SIZE_BYTES = 4096;
    private boolean isRunning = true;

    public Client(String host, int port) {
        serverHost = host;
        serverPort = port;
        this.scannerManager = new ScannerManager();
        this.commandHandler = new CommandHandler(scannerManager, new ArgumentRequester(scannerManager));
    }

    public void run() {
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++){
            boolean isOk = createSocketAndRun(attempt);
            if (isOk) break;
        }
        shutdown();
    }

    private boolean createSocketAndRun(int attempt){
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(serverHost, serverPort), RETRY_DELAY_MS);
            ClientProtocol clientProtocol = new ClientProtocol(socket);

            //Объеденить как и с нижним
            Packet packet = clientProtocol.receive();
            ServerResponse response = clientProtocol.deserializeFromBytes(packet.getData(), ServerResponse.class);
            System.out.println(response.getValue());

            while (isRunning && scannerManager.hasNextLine()) {
                String inputString = input();
                if (inputString == null) continue;

                CommandArgumentList commandArgList = handleCommand(inputString);
                if (commandArgList == null) continue;
                if (commandArgList.getCommand().toString().equals("exit")) return true;

                ClientRequest request = new ClientRequest(ClientStatus.REQUEST, commandArgList);
                communicateWithServer(clientProtocol, request, socket);
            }
        } catch (SocketException e) {
            handleRetry(attempt, e);
        } catch (Exception e) {
            System.out.println("Unexpected Exception: " + e.getMessage());
        }
        return false;
    }

    private void communicateWithServer(ClientProtocol clientProtocol, ClientRequest request, Socket socket) throws SocketException{
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            try{
                clientProtocol.send(request);

                //Убрать пакет, сделать нормальный generic метод receive
                Packet packet = clientProtocol.receive();
                ServerResponse response = clientProtocol.deserializeFromBytes(packet.getData(), ServerResponse.class);

                switch (response.getStatus()) {
                    case EXIT:
                        isRunning = false;
                        return;
                    case EXCEPTION:
                        System.err.println("Server exception:\n" + response);
                        return;
                    default:
                        System.out.println("Server response:\n" + response.getValue());
                        return;
                }
            } catch (SocketTimeoutException e) {
                handleRetry(attempt, e);
            } catch (IOException e) {
                if (e.getMessage().equals("Connection reset by peer")) {
                    throw new SocketException("Server closed!");
                }
                System.out.println("Communication error: " + e.getMessage());
                return;
            }
        }
        System.out.println("Server unavailable after " + MAX_RETRIES + " attempts.");
    }

//    private ServerResponse receiveResponse(Socket socket) throws IOException {
//        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
//        StringBuilder responseBuilder = new StringBuilder();
//        char[] buffer = new char[BUFFER_SIZE_BYTES];
//        int status;
//
//        while ((status = in.read(buffer)) > 0) {
//            responseBuilder.append(buffer);
//            if (status < BUFFER_SIZE_BYTES){
//                break;
//            }
//        }
//        return objectMapper.readValue(responseBuilder.toString(), ServerResponse.class);
//    }

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