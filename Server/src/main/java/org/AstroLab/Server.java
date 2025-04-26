package org.AstroLab;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.AstroLab.collection.CustomCollection;
import org.AstroLab.files.JsonReader;
import org.AstroLab.files.Reader;
import org.AstroLab.serverCommand.CommandManager;
import org.AstroLab.utils.ClientServer.ClientRequest;
import org.AstroLab.utils.ClientServer.ProtocolUtils;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    private static final int TIMEOUT_MS = 1000;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private CommandManager commandManager;
    private String host;
    private int port;
    private boolean isRunning = true;

    public Server(String[] args) {
        if (args.length < 2){
            logger.error("Not enough parameters (It have to be 2: <host port>");
            isRunning = false;
            return;
        }

        try{
            this.host = args[0];
            this.port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e){
            logger.error("Your port is broken: {}", e.getMessage());
            isRunning = false;
            return;
        }

        initCollectionCmdManager();
    }

    public void run() {
        if (!isRunning){
            return;
        }

        try (ServerSocketChannel serverChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            try {
                serverChannel.bind(new InetSocketAddress(host, port));
                serverChannel.configureBlocking(false);
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            } catch (IllegalArgumentException | SecurityException | AlreadyBoundException e){
                logger.error("You can't bind server{ip={}, port={}}, because: {}", this.host, this.port, e.getMessage());
            } catch (ClosedChannelException | ClosedSelectorException e) {
                logger.error("Your server's channel or selector closed! {}", e.getMessage());
            }

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
            logger.error("Server error: {}", e.getMessage());
        }
    }

    private void handleAccept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();

        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);

        logger.info("Accepted connection from: {}", clientChannel.getRemoteAddress());
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        try {
            ByteBuffer buffer = ProtocolUtils.readCompleteMessage(clientChannel);
            ClientRequest clientRequest = ProtocolUtils.deserializeFromBuffer(buffer, ClientRequest.class);
            ServerResponse response = executeCommandSafely(clientRequest.getRequest());
            sendResponse(clientChannel, response);

        } catch (IOException e) {
            handleClientDisconnect(clientChannel, key);
            return;
        }
        key.interestOps(SelectionKey.OP_READ);
    }

    private ServerResponse executeCommandSafely(CommandArgumentList command) {
        try {
            return commandManager.executeCommand(command);
        } catch (Exception e) {
            return new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
    }

    private void sendResponse(SocketChannel channel, ServerResponse response) throws IOException {
        ByteBuffer responseBuffer = ProtocolUtils.serializeToBuffer(response);
        while (responseBuffer.hasRemaining()) {
            channel.write(responseBuffer);
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
        } catch (JsonMappingException e) {
            logger.error("Program can't parse your Json file, check this error and try fix it!\n\t{}", e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            logger.error("Ops... Exception while reading!\n{}", e.getMessage());
            System.exit(-1);
        }

    }

}