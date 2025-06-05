package AstroLabClient.communicator;

import AstroLab.auth.UserDTO;
import AstroLab.grpc.*;
import AstroLabClient.clientAction.ClientAction;
import AstroLab.actions.components.ClientServerAction;
import AstroLabClient.clientAction.CommandVisitor;
import java.rmi.ServerException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;

public class Communicator implements CommandVisitor {
    private final ManagedChannel channel;
    private final AstroAuthServiceGrpc.AstroAuthServiceBlockingStub authStub;
    private final AstroCommandServiceGrpc.AstroCommandServiceBlockingStub commandStub;
    private String jwtToken;;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Communicator(String serverHost, int grpcPort, UserDTO userDTO) throws Exception {
        this.channel = ManagedChannelBuilder.forAddress(serverHost, grpcPort)
                .usePlaintext()
                .build();
        this.authStub = AstroAuthServiceGrpc.newBlockingStub(channel);
        this.commandStub = AstroCommandServiceGrpc.newBlockingStub(channel);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
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
                this.jwtToken = response.getToken();
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

    public <T> void communicate(T action) throws Exception {
        if (action instanceof ClientAction) {
            visitIt((ClientAction) action);
        } else if (action instanceof ClientServerAction) {
            visitIt((ClientServerAction) action);
        } else if (action == null) {
            System.out.println("Null action, skipping communication.");
        }
        else {
            System.err.println("Unknown action type: " + action.getClass().getName());
        }
    }

    @Override
    public void visitIt(ClientServerAction clientServerAction) throws Exception {
        if (jwtToken == null) {
            throw new IllegalStateException("Not authenticated. Cannot send command.");
        }

        String actionJson = objectMapper.writeValueAsString(clientServerAction);
        ClientRequestProto request = ClientRequestProto.newBuilder()
                .setActionJson(actionJson)
                .setToken(jwtToken)
                .build();

        System.out.println("Sending gRPC command...");
        try {
            int DEADLINE_SECONDS = 5;
            ServerResponseProto response = commandStub.withDeadlineAfter(DEADLINE_SECONDS, TimeUnit.SECONDS)
                    .executeCommand(request);

            Object responseObject = null;
            if(response.getValueJson() != null && !response.getValueJson().isEmpty()){
                try {
                    if (response.getValueJson().trim().startsWith("{")) {
                        responseObject = objectMapper.readValue(response.getValueJson(), java.util.Map.class);
                    } else if (response.getValueJson().trim().startsWith("[")) {
                        responseObject = objectMapper.readValue(response.getValueJson(), java.util.List.class);
                    } else {
                        responseObject = objectMapper.readValue(response.getValueJson(), String.class);
                    }
                } catch (Exception e) {
                    System.err.println("Could not deserialize response value JSON: " + response.getValueJson() + " Error: " + e.getMessage());
                    responseObject = response.getValueJson();
                }
            }

            System.out.println("gRPC Server Response {" + response.getStatus() + "}" + (responseObject != null ? responseObject.toString() : "null"));

            if (response.getStatus() == ResponseStatusProto.EXCEPTION || response.getStatus() == ResponseStatusProto.UNAUTHORIZED) {
                System.err.println("Server error: " + (responseObject != null ? responseObject.toString() : "No details"));
            }

        } catch (StatusRuntimeException e) {
            System.err.println("gRPC Command request failed: " + e.getStatus());
            if (e.getStatus().getCode() == io.grpc.Status.Code.UNAVAILABLE) {
                throw new ServerException("Server unavailable!");
            }
        }
    }

    @Override
    public void visitIt(ClientAction clientAction) throws Exception {
        clientAction.executeLocally();
    }

    public void shutdown() throws InterruptedException {
        if (channel != null) {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}