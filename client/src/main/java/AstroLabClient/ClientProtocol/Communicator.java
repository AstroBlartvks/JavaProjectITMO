package AstroLabClient.ClientProtocol;

import AstroLab.auth.UserDTO;
import AstroLabClient.clientAction.ClientAction;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.actions.components.Action;
import AstroLab.utils.ClientServer.ClientRequest;
import AstroLab.utils.ClientServer.ClientStatus;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLabClient.clientAction.CommandVisitor;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.rmi.ServerException;
import java.util.Objects;

public class Communicator implements CommandVisitor {
    private final ClientProtocol clientProtocol;
    private final UserDTO userDTO;
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 5000;

    public Communicator(Socket socket, String serverHost, int serverPort, UserDTO userDTO) throws IOException {
        socket.setSoTimeout(2 * RETRY_DELAY_MS);
        socket.connect(new InetSocketAddress(serverHost, serverPort), RETRY_DELAY_MS);
        this.clientProtocol = new ClientProtocol(socket);
        this.userDTO = userDTO;
        initConnection();
    }

    private void initConnection() throws SocketTimeoutException, ConnectException {
        clientProtocol.send(this.userDTO);
        ServerResponse response = getResponse();
        System.out.println(response.getValue());

        if (response.getStatus() == ResponseStatus.FORBIDDEN){
            throw new ConnectException("Connection closed by server!");
        }
    }

    private ServerResponse getResponse() throws SocketTimeoutException {
        return clientProtocol.receive(ServerResponse.class);
    }

    public void communicate(Action action) throws Exception {
        if (action instanceof ClientAction) {
            visitIt((ClientAction) action);
        } else if (action instanceof ClientServerAction) {
            visitIt((ClientServerAction) action);
        }
    }

    @Override
    public void visitIt(ClientServerAction clientServerAction) throws Exception {
        ClientRequest request = new ClientRequest(ClientStatus.REQUEST, clientServerAction);
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            clientProtocol.send(request);
            ServerResponse response;

            try {
                response = clientProtocol.receive(ServerResponse.class);
            } catch (SocketTimeoutException e) {
                System.out.println("Attempt to get response №" + attempt);
                continue;
            }

            if (response == null) continue;

            if (Objects.requireNonNull(response.getStatus()) == ResponseStatus.EXCEPTION) {
                System.out.println("Server exception:\n" + response);
                return;
            }
            System.out.println("Server response:\n" + response.getValue());
            return;
        }
        System.out.println("Server unavailable after " + MAX_RETRIES + " attempts.");
        throw new ServerException("Server Closed!");
    }

    @Override
    public void visitIt(ClientAction clientAction) throws Exception {
        clientAction.executeLocally();
    }
}