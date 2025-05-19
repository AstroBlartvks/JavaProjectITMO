package AstroLabServer.onlyServerCommand;

import AstroLab.auth.UserDTO;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.tcpProtocol.packet.ClientClosedConnectionException;
import AstroLab.utils.tcpProtocol.packet.PacketIsNullException;
import AstroLabServer.auth.AuthService;
import AstroLabServer.auth.AuthStates;
import AstroLabServer.serverProtocol.ServerProtocol;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.sasl.AuthenticationException;


public class ServerUtils {
    public static final Logger LOGGER = LogManager.getLogger(ServerUtils.class);
    private final ServerCommandManager serverCommandManager;
    private final AuthService authService;

    public ServerUtils(ServerCommandManager serverCommandManager, AuthService authService){
        this.serverCommandManager = serverCommandManager;
        this.authService = authService;
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

    public void handleNewConnection(SelectionKey key, Selector selector) throws IOException, SQLException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();

        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);

        ServerProtocol serverProtocol = new ServerProtocol(clientChannel);
        UserDTO userDTO = serverProtocol.receive(UserDTO.class);
        LOGGER.info("Accepted connection from {}, User tried to connect: {}", clientChannel.getRemoteAddress(), userDTO);

        AuthStates authStates = isAuthenticated(userDTO);

        if (authStates.isState()){
            LOGGER.info("User logged successfully: {}", userDTO);
            handleSendResponse(clientChannel, new ServerResponse(ResponseStatus.OK, authStates.getMessage()));
        } else {
            LOGGER.info("User didn't login: {}, because: {}", userDTO, authStates.getMessage());
            handleSendResponse(clientChannel, new ServerResponse(ResponseStatus.FORBIDDEN, authStates.getMessage()));
            clientChannel.close();
            throw new AuthenticationException(userDTO + " is undefined!");
        }

    }

    public AuthStates isAuthenticated(UserDTO userDTO) throws SQLException {
        return switch (userDTO.getConnectionType()) {
            case LOGIN -> authService.login(userDTO);
            case REGISTER -> authService.register(userDTO);
        };
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
            LOGGER.error("Error while reading request: {}", e.getMessage());
            handleClientDisconnect(clientChannel, key);
            return null;
        }
        key.interestOps(SelectionKey.OP_READ);

        return clientRequest;
    }

    public void handleSendResponse(SocketChannel channel, ServerResponse response) {
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

    public void closeChannel(SelectionKey key) {
        try {
            LOGGER.info("Closing connection: {}", ((SocketChannel) key.channel()).getRemoteAddress());
            key.channel().close();
        } catch (IOException ex) {
            LOGGER.error("Error closing channel: {}", ex.getMessage());
        }
    }
}
