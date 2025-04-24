package org.javaLab6;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaLab6.collection.CustomCollection;
import org.javaLab6.files.JsonReader;
import org.javaLab6.files.Reader;
import org.javaLab6.serverCommand.CommandManager;
import org.javaLab6.utils.ClientServer.ClientRequest;
import org.javaLab6.utils.ClientServer.ResponseStatus;
import org.javaLab6.utils.ClientServer.ServerResponse;
import org.javaLab6.utils.command.CommandArgumentList;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Server {
    private static final int TIMEOUT_MS = 1000;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private CommandManager commandManager;
    private final String host;
    private final int port;
    private final boolean isRunning = true;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
        initCollectionCmdManager();
    }

    public void run() {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            serverChannel.bind(new InetSocketAddress(host, port));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server started on " + host + ":" + port);

            while (isRunning) {
                if (selector.select(TIMEOUT_MS) == 0) {
                    continue;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = keys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    try {
                        if (key.isAcceptable()) {
                            handleAccept(key, selector);
                        } else if (key.isReadable()) {
                            handleRead(key);
                        }
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        closeChannel(key);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private void handleAccept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();

        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);

        System.out.println("Accepted connection from: "
                + clientChannel.getRemoteAddress());
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder messageBuilder = new StringBuilder();

        while (clientChannel.read(buffer) > 0) {
            buffer.flip();
            messageBuilder.append(StandardCharsets.UTF_8.decode(buffer));
            buffer.clear();
        }

        String fullMessage = messageBuilder.toString().trim();
        ClientRequest clientRequest = objectMapper.readValue(fullMessage, ClientRequest.class);

        CommandArgumentList command = new CommandArgumentList();
        command.setArgList(clientRequest.getRequest());
        ServerResponse response;
        try {
            response = commandManager.executeCommand(command);
        } catch (Exception e) {
            response = new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }

        if (response == null) response = new ServerResponse(ResponseStatus.EXCEPTION, "Unknown Exception");

        String jsonResponse = objectMapper.writeValueAsString(response) + "\n";
        ByteBuffer responseBuffer = ByteBuffer.wrap(jsonResponse.getBytes(StandardCharsets.UTF_8));
        while (responseBuffer.hasRemaining()) {
            clientChannel.write(responseBuffer);
        }

        key.interestOps(SelectionKey.OP_READ);
        clientChannel.close();
    }

    private void closeChannel(SelectionKey key) {
        try {
            System.out.println("Closing connection: "
                    + ((SocketChannel) key.channel()).getRemoteAddress());
            key.channel().close();
        } catch (IOException ex) {
            System.err.println("Error closing channel: " + ex.getMessage());
        }
    }

    private void initCollectionCmdManager(){
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try{
            Reader reader = new Reader(new JsonReader());
            CustomCollection customCollection = reader.readFromEnv();
            commandManager = new CommandManager(customCollection);
        } catch (JsonMappingException e) {
            System.err.println("Program can't parse your Json file, check this error and try fix it!\n\t" + e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            System.err.println("Ops... Exception while reading!\n" + e.getMessage());
            System.exit(-1);
        }

    }

}