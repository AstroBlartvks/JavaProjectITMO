package AstroLabClient.clientAction;

import AstroLab.actions.components.Action;
import AstroLab.auth.UserDTO;
import AstroLab.grpc.*;

import java.rmi.ServerException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;

public class Communicator {
    private final ManagedChannel channel;
    private final AstroAuthServiceGrpc.AstroAuthServiceBlockingStub authStub;
    private final ActionVisitor actionVisitor = new ActionVisitor();

    public Communicator(String serverHost, int grpcPort, UserDTO userDTO) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        this.channel = ManagedChannelBuilder.forAddress(serverHost, grpcPort)
                .usePlaintext()
                .build();
        this.authStub = AstroAuthServiceGrpc.newBlockingStub(channel);
        AstroCommandServiceGrpc.AstroCommandServiceBlockingStub commandStub = AstroCommandServiceGrpc.newBlockingStub(channel);
        actionVisitor.setCommandStub(commandStub);
        actionVisitor.setObjectMapper(objectMapper);
        initConnection(userDTO);
    }

    private void initConnection(UserDTO userDTO) throws Exception {
        UserDTOProto.Builder requestBuilder = UserDTOProto.newBuilder()
                .setLogin(userDTO.getLogin())
                .setPassword(userDTO.getPassword());

        if (userDTO.getConnectionType() == AstroLab.auth.ConnectionType.LOGIN) {
            requestBuilder.setConnectionType(ConnectionTypeProto.LOGIN);
        } else {
            requestBuilder.setConnectionType(ConnectionTypeProto.REGISTER);
        }

        System.out.println("Attempting gRPC authentication for: " + userDTO.getLogin());
        try {
            AuthResponseProto response = authStub.withDeadlineAfter(5, TimeUnit.SECONDS) // Add timeout
                    .authenticate(requestBuilder.build());

            if (response.getStatus() == ResponseStatusProto.OK) {
                String jwtToken = response.getToken();
                this.actionVisitor.setJwtToken(jwtToken);
                System.out.println("gRPC Auth successful: " + response.getMessage());
            } else {
                System.out.println("gRPC Auth failed: " + response.getMessage());
                throw new SecurityException("Authentication failed: " + response.getMessage());
            }
        } catch (StatusRuntimeException e) {
            System.err.println("gRPC Auth request failed: " + e.getStatus());
            throw new ServerException("Server communication error during auth: " + e.getMessage());
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