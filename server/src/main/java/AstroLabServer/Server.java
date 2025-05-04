package AstroLabServer;

import AstroLab.actions.components.Action;
import AstroLab.utils.ClientServer.ClientRequest;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.tcpProtocol.packet.ClientClosedConnectionException;
import AstroLab.utils.tcpProtocol.packet.PacketIsNullException;
import AstroLabServer.ServerProtocol.ServerProtocol;
import AstroLabServer.collection.CustomCollection;
import AstroLabServer.files.JsonReader;
import AstroLabServer.files.Reader;
import AstroLabServer.onlyServerCommand.OnlyServerResult;
import AstroLabServer.onlyServerCommand.ServerCommandManager;
import AstroLabServer.serverCommand.CommandManager;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    public static final Logger LOGGER = LogManager.getLogger(Server.class);
    private static final int TIMEOUT_MS = 1000;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ServerCommandManager serverCommandManager;
    private CommandManager commandManager;
    private final String serverHost;
    private final int serverPort;
    private boolean isRunning;

    public Server(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
        this.isRunning = true;
        initCollectionCmdManager();
    }

    public void run() {
        try (ServerSocketChannel serverChannel =
                     ServerSocketChannel.open(); Selector selector = Selector.open()) {
            try {
                serverChannel.bind(new InetSocketAddress(serverHost, serverPort));
                serverChannel.configureBlocking(false);
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            } catch (IllegalArgumentException | SecurityException | AlreadyBoundException e) {
                LOGGER.fatal("You can't bind server{ip={}, port={}}, because: {}", this.serverHost, this.serverPort, e.getMessage());
            } catch (ClosedChannelException | ClosedSelectorException e) {
                LOGGER.fatal("Your server's channel or selector closed! {}", e.getMessage());
            }

            LOGGER.info("Server started on {}:{}", serverHost, serverPort);

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
                        LOGGER.error(e.getMessage());
                        closeChannel(key);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Server error: {}", e.getMessage());
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
                LOGGER.warn("The exception while handling command!");
                break;
            default:
                LOGGER.info("Command result: {}", result);
        }
    }

    private OnlyServerResult readCommandFromConsole() throws IOException {
        if (System.in.available() > 0) {
            byte[] buffer = new byte[System.in.available()];
            int res = System.in.read(buffer);
            if (res == '\n' || res == -1) {
                return OnlyServerResult.OK;
            }
            String str = new String(buffer, StandardCharsets.UTF_8);
            try {
                return serverCommandManager.executeCommand(str.trim());
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
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

        LOGGER.info("Accepted connection from: {}", clientChannel.getRemoteAddress());
        handleSendResponse(clientChannel, new ServerResponse(ResponseStatus.OK, "You have connected to the server!"));
    }

    private ClientRequest handleReadRequest(SelectionKey key, SocketChannel clientChannel) throws IOException {
        ClientRequest clientRequest;
        ServerProtocol serverProtocol = new ServerProtocol(clientChannel);
        try {
            clientRequest = serverProtocol.receive(ClientRequest.class);
            LOGGER.info("Request from client={}: {}", clientChannel, clientRequest);
        } catch (PacketIsNullException | ClientClosedConnectionException e) {
            LOGGER.error("Error while reading request: {}", e.getMessage());
            handleClientDisconnect(clientChannel, key);
            return null;
        }
        key.interestOps(SelectionKey.OP_READ);

        return clientRequest;
    }

    private ServerResponse executeCommandSafely(Action command) {
        try {
            return commandManager.executeCommand(command);
        } catch (Exception e) {
            return new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
    }

    private void handleSendResponse(SocketChannel channel, ServerResponse response) {
        LOGGER.info("Server response to client={}: {}", channel, response.getStatus());
        ServerProtocol serverProtocol = new ServerProtocol(channel);
        try {
            serverProtocol.send(response);
        } catch (Exception e) {
            LOGGER.error("Error while sending request: {}", e.getMessage());
        }
    }

    private void handleClientDisconnect(SocketChannel channel, SelectionKey key) throws IOException {
        channel.close();
        key.cancel();
    }

    private void closeChannel(SelectionKey key) {
        try {
            LOGGER.info("Closing connection: {}", ((SocketChannel) key.channel()).getRemoteAddress());
            key.channel().close();
        } catch (IOException ex) {
            LOGGER.error("Error closing channel: {}", ex.getMessage());
        }
    }

    private void initCollectionCmdManager() {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            Reader reader = new Reader(new JsonReader());
            CustomCollection customCollection = reader.readFromEnv();
            commandManager = new CommandManager(customCollection);
            serverCommandManager = new ServerCommandManager(customCollection);
        } catch (JsonMappingException e) {
            LOGGER.error("Program can't parse your Json file, check this error and try fix it!\n\t{}", e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            LOGGER.error("Ops... Exception while reading!\n{}", e.getMessage());
            System.exit(-1);
        }
    }
}