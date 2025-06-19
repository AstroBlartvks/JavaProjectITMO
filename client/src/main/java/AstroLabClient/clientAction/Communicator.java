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

    private String initConnection(UserDTO userDTO) {
        if (userDTO.getConnectionType() == AstroLab.auth.ConnectionType.LOGIN) {
            LoginRequest request = LoginRequest.newBuilder()
                    .setLogin(userDTO.getLogin())
                    .setPassword(userDTO.getPassword())
                    .build();

            System.out.println("Attempting gRPC login for: " + userDTO.getLogin());
            AuthResponseMessage response = authStub.withDeadlineAfter(5, TimeUnit.SECONDS)
                    .login(request);

            return processAuthResponse(response);
        } else {
            RegisterRequest request = RegisterRequest.newBuilder()
                    .setLogin(userDTO.getLogin())
                    .setPassword(userDTO.getPassword())
                    .build();

            System.out.println("Attempting gRPC registration for: " + userDTO.getLogin());
            AuthResponseMessage response = authStub.withDeadlineAfter(5, TimeUnit.SECONDS)
                    .register(request);

            return processAuthResponse(response);
        }
    }

    private String processAuthResponse(AuthResponseMessage response) {
        if (response.getStatus() == ResponseStatusMessage.OK) {
            String jwtToken = response.getToken();
            System.out.println("gRPC Auth successful: " + response.getMessage());
            return jwtToken;
        } else {
            System.out.println("gRPC Auth failed: " + response.getMessage());
            throw new SecurityException("Authentication failed: " + response.getMessage());
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