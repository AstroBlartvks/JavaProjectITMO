package AstroLabServer.onlyServerCommand;

import AstroLab.actions.components.Action;
import AstroLab.auth.UserDTO;
import AstroLab.utils.ClientServer.ClientRequest;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.tcpProtocol.packet.ClientClosedConnectionException;
import AstroLab.utils.tcpProtocol.packet.PacketIsNullException;
import AstroLabServer.auth.AuthService;
import AstroLabServer.auth.AuthStates;
import AstroLabServer.serverCommand.CommandManager;
import AstroLabServer.serverProtocol.ServerProtocol;

import java.io.IOException;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
public class ServerUtils {
    public static final Logger LOGGER = LogManager.getLogger(ServerUtils.class);
    private final ServerCommandManager serverCommandManager;
    private final AuthService authService;
    private final ExecutorService processPool;
    private final CommandManager commandManager;

    public ServerUtils(ServerCommandManager serverCommandManager,
                       AuthService authService,
                       ExecutorService processPool,
                       CommandManager commandManager){
        this.serverCommandManager = serverCommandManager;
        this.authService = authService;
        this.processPool = processPool;
        this.commandManager = commandManager;
    }

    public OnlyServerResult readConsoleCommand() throws IOException {
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
        return OnlyServerResult.NOTHING;
    }

    public void handleNewConnection(SelectionKey key, Selector selector) {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        try {
            SocketChannel clientChannel = serverChannel.accept();
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
            key.attach(new ServerProtocol(clientChannel));

            ServerProtocol proto = (ServerProtocol) key.attachment();
            UserDTO userDTO = proto.receive(UserDTO.class);

            LOGGER.info("Accepted connection from {}, User tried to connect: {}", clientChannel.getRemoteAddress(), userDTO);
            registerUser(clientChannel, userDTO, selector);

        } catch (IOException e) {
            LOGGER.error("Failed to accept new connection: {}", e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("Database error: {}", e.getMessage());
        }
    }

    public void registerUser(SocketChannel clientChannel, UserDTO userDTO, Selector selector) throws IOException, SQLException {
        AuthStates authStates = isAuthenticated(userDTO);
        try {
            if (authStates.isState()) {
                LOGGER.info("User logged successfully: {}", userDTO);
                handleSendResponse(clientChannel, new ServerResponse(ResponseStatus.OK, authStates.getMessage()));
                clientChannel.register(selector, SelectionKey.OP_READ);
            } else {
                LOGGER.info("User didn't login: {}, because: {}", userDTO, authStates.getMessage());
                handleSendResponse(clientChannel, new ServerResponse(ResponseStatus.FORBIDDEN, authStates.getMessage()));
            }
        } finally {
            if (!authStates.isState()) {
                clientChannel.close();
            }
        }
    }

    public boolean loginUser(UserDTO userDTO) throws SQLException {
        AuthStates authStates = isAuthenticated(userDTO);
        return authStates.isState();
    }

    private AuthStates isAuthenticated(UserDTO userDTO) throws SQLException {
        return switch (userDTO.getConnectionType()) {
            case LOGIN -> authService.login(userDTO);
            case REGISTER -> authService.register(userDTO);
        };
    }

    public ServerResponse executeCommandSafely(ClientRequest clientRequest) {
        try {
            Action action = clientRequest.getRequest();
            return commandManager.executeCommand(action);
        } catch (Exception e) {
            return new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
    }

    public <T> T readClientRequest(SelectionKey key,
                                   SocketChannel clientChannel,
                                   Class<T> type) throws IOException {
        T clientRequest;
        ServerProtocol serverProtocol = new ServerProtocol(clientChannel);
        try {
            clientRequest = serverProtocol.receive(type);
            LOGGER.info("Request from client={}: {}", clientChannel, clientRequest);
        } catch (PacketIsNullException | ClientClosedConnectionException e) {
            handleClientDisconnect(clientChannel, key);
            return null;
        }
        key.interestOps(SelectionKey.OP_READ);

        return clientRequest;
    }

    public void handleSendResponse(SocketChannel channel, ServerResponse response) {
        if (channel == null || !channel.isOpen()) {
            return;
        }
        LOGGER.info("Server response to client={}: {}", channel, response.getStatus());
        ServerProtocol serverProtocol = new ServerProtocol(channel);
        try {
            serverProtocol.send(response);
        } catch (Exception e) {
            LOGGER.error("Error sending response: {}", e.getMessage());
        }
    }

    private void handleClientDisconnect(SocketChannel channel, SelectionKey key) {
        try {
            if (key != null) {
                key.cancel();
            }
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
        } catch (IOException e) {
            LOGGER.error("Error while disconnecting client and closing channel: {}", e.getMessage());
        }
    }

    public void closeChannel(SelectionKey key) {
        try {
            LOGGER.info("Closing connection with client!");
            key.channel().close();
        } catch (IOException ex) {
            LOGGER.error("Error closing channel: {}", ex.getMessage());
        }
    }
}