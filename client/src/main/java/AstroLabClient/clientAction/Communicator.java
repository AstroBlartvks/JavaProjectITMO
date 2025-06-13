package AstroLabClient.clientAction;

import AstroLab.actions.components.Action;
import AstroLab.auth.UserDTO;
import AstroLab.grpc.*;
import io.grpc.*;

import java.util.concurrent.TimeUnit;

public class Communicator {
    private final ManagedChannel channel;
    private final AstroAuthServiceGrpc.AstroAuthServiceBlockingStub authStub;
    private final ActionVisitor actionVisitor = new ActionVisitor();

    public Communicator(String serverHost, int grpcPort, UserDTO userDTO) throws Exception {

        this.channel = ManagedChannelBuilder.forAddress(serverHost, grpcPort)
                .usePlaintext()
                .build();
        this.authStub = AstroAuthServiceGrpc.newBlockingStub(channel);

        String jwtToken = initConnection(userDTO);
        JwtClientInterceptor interceptor = new JwtClientInterceptor(jwtToken);
        Channel interceptedChannel = ClientInterceptors.intercept(channel, interceptor);
        AstroCommandServiceGrpc.AstroCommandServiceBlockingStub commandStub = AstroCommandServiceGrpc.newBlockingStub(interceptedChannel);

        actionVisitor.setCommandStub(commandStub);
        actionVisitor.setJwtToken(jwtToken);
    }

    private String initConnection(UserDTO userDTO) throws Exception {
        UserDTOMessage.Builder requestBuilder = UserDTOMessage.newBuilder()
                .setLogin(userDTO.getLogin())
                .setPassword(userDTO.getPassword());

        if (userDTO.getConnectionType() == AstroLab.auth.ConnectionType.LOGIN) {
            requestBuilder.setConnectionType(ConnectionTypeMessage.LOGIN);
        } else {
            requestBuilder.setConnectionType(ConnectionTypeMessage.REGISTER);
        }

        System.out.println("Attempting gRPC authentication for: " + userDTO.getLogin());
        try {
            AuthResponseMessage response = authStub.withDeadlineAfter(5, TimeUnit.SECONDS)
                    .authenticate(requestBuilder.build());

            if (response.getStatus() == ResponseStatusMessage.OK) {
                String jwtToken = response.getToken();
                System.out.println("gRPC Auth successful: " + response.getMessage());
                return jwtToken;
            } else {
                System.out.println("gRPC Auth failed: " + response.getMessage());
                throw new SecurityException("Authentication failed: " + response.getMessage());
            }
        } catch (StatusRuntimeException e) {
            System.err.println("gRPC Auth request failed: " + e.getStatus());
            throw new Exception("Server communication error during auth: " + e.getMessage());
        }
    }

    public void communicate(Action action) throws Exception {
        action.accept(actionVisitor);
    }

    public void shutdown() throws InterruptedException {
        if (channel != null) {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}