package org.AstroLabServer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.AstroLab.utils.tcpProtocol.packet.ClientClosedConnectionException;
import org.AstroLab.utils.tcpProtocol.packet.PacketIsNullException;
import org.AstroLabServer.ServerProtocol.ServerProtocol;
import org.AstroLabServer.collection.CustomCollection;
import org.AstroLabServer.files.JsonReader;
import org.AstroLabServer.files.Reader;
import org.AstroLabServer.onlyServerCommand.OnlyServerResult;
import org.AstroLabServer.onlyServerCommand.ServerCommandManager;
import org.AstroLabServer.serverCommand.CommandManager;
import org.AstroLab.utils.ClientServer.ClientRequest;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Server {
    public static final Logger logger = LogManager.getLogger(Server.class);
    private static final int TIMEOUT_MS = 1000;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ServerCommandManager serverCommandManager;
    private CommandManager commandManager;
    private final String serverHost;
    private final int serverPort;
    private boolean isRunning;
    private ReadableByteChannel consoleChannel;

    public Server(String host, int port){
        this.serverHost = host;
        this.serverPort = port;
        this.isRunning = true;
        initCollectionCmdManager();
    }

    public void run() {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            try {
                serverChannel.bind(new InetSocketAddress(serverHost, serverPort));
                serverChannel.configureBlocking(false);
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            } catch (IllegalArgumentException | SecurityException | AlreadyBoundException e){
                logger.fatal("You can't bind server{ip={}, port={}}, because: {}", this.serverHost, this.serverPort, e.getMessage());
            } catch (ClosedChannelException | ClosedSelectorException e) {
                logger.fatal("Your server's channel or selector closed! {}", e.getMessage());
            }

            logger.info("Server started on {}:{}", serverHost, serverPort);

            while (isRunning) {
                handleConsoleCommand();

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
                            SocketChannel clientChannel = (SocketChannel) key.channel();
                            ClientRequest clientRequest = handleReadRequest(key, clientChannel);
                            if (clientRequest == null) continue;
                            ServerResponse response = executeCommandSafely(clientRequest.getRequest());
                            handleSendResponse(clientChannel, response);
                        }
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                        closeChannel(key);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Server error: {}", e.getMessage());
        }
    }

    private void handleConsoleCommand() throws Exception {
        OnlyServerResult result = readCommandFromConsole();

        switch (result) {
            case EXIT:
                serverCommandManager.executeCommand("save");
                isRunning = false;
                break;
            case EXCEPTION:
                logger.warn("The exception while handling command!");
                break;
        }
    }

    private OnlyServerResult readCommandFromConsole() throws IOException{
        if (System.in.available() > 0){
            byte[] buffer = new byte[System.in.available()];
            int res = System.in.read(buffer);
            if (res == '\n' || res == -1){
                return OnlyServerResult.OK;
            }
            String str = new String(buffer, StandardCharsets.UTF_8);
            try {
                return serverCommandManager.executeCommand(str.trim());
            } catch (Exception e){
                logger.error(e.getMessage());
                return OnlyServerResult.EXCEPTION;
            }
        }
        return OnlyServerResult.OK;
    }

    private void handleAccept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();

        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);

        logger.info("Accepted connection from: {}", clientChannel.getRemoteAddress());
        handleSendResponse(clientChannel, new ServerResponse(ResponseStatus.OK, "You have connected to the server!"));
    }

    private ClientRequest handleReadRequest(SelectionKey key, SocketChannel clientChannel) throws IOException {
        ClientRequest clientRequest;
        ServerProtocol serverProtocol = new ServerProtocol(clientChannel);
        try {
            clientRequest = serverProtocol.receive(ClientRequest.class);
            logger.info("Request from client={}: {}", clientChannel, clientRequest);
        } catch (PacketIsNullException | ClientClosedConnectionException e) {
            logger.error("Error while reading request: {}", e.getMessage());
            handleClientDisconnect(clientChannel, key);
            return null;
        }
        key.interestOps(SelectionKey.OP_READ);

        return clientRequest;
    }

    private ServerResponse executeCommandSafely(CommandArgumentList command) {
        try {
            return commandManager.executeCommand(command);
        } catch (Exception e) {
            return new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
    }

    private void handleSendResponse(SocketChannel channel, ServerResponse response) throws IOException {
        logger.info("Server response to client={}: {}", channel, response.getStatus());
        ServerProtocol serverProtocol = new ServerProtocol(channel);
        try {
            serverProtocol.send(response);
        } catch (Exception e) {
            logger.error("Error while sending request: {}", e.getMessage());
        }
    }

    private void handleClientDisconnect(SocketChannel channel, SelectionKey key) throws IOException {
        channel.close();
        key.cancel();
    }

    private void closeChannel(SelectionKey key) {
        try {
            logger.info("Closing connection: {}", ((SocketChannel) key.channel()).getRemoteAddress());
            key.channel().close();
        } catch (IOException ex) {
            logger.error("Error closing channel: {}", ex.getMessage());
        }
    }

    private void initCollectionCmdManager(){
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try{
            Reader reader = new Reader(new JsonReader());
            CustomCollection customCollection = reader.readFromEnv();
            commandManager = new CommandManager(customCollection);
            serverCommandManager = new ServerCommandManager(customCollection);
        } catch (JsonMappingException e) {
            logger.error("Program can't parse your Json file, check this error and try fix it!\n\t{}", e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            logger.error("Ops... Exception while reading!\n{}", e.getMessage());
            System.exit(-1);
        }

    }

}