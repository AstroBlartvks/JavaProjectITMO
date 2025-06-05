package AstroLabClient.clientAction;

import AstroLab.actions.components.ClientAction;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.actions.utils.ActionVisitable;
import AstroLab.grpc.AstroCommandServiceGrpc;
import AstroLab.grpc.ClientRequestProto;
import AstroLab.grpc.ResponseStatusProto;
import AstroLab.grpc.ServerResponseProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.StatusRuntimeException;
import lombok.Getter;
import lombok.Setter;

import java.rmi.ServerException;
import java.util.concurrent.TimeUnit;

@Setter
@Getter
public class ActionVisitor implements ActionVisitable {
    private String jwtToken;
    private AstroCommandServiceGrpc.AstroCommandServiceBlockingStub commandStub;
    private ObjectMapper objectMapper;

    @Override
    public void visit(ClientAction clientAction) throws Exception{
        clientAction.executeLocally();
    }

    @Override
    public void visit(ClientServerAction clientServerAction) throws JsonProcessingException, ServerException {
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
            int durationBeforeDeath = 5;
            ServerResponseProto response = commandStub.withDeadlineAfter(durationBeforeDeath, TimeUnit.SECONDS)
                    .executeCommand(request);

            Object responseObject = null;
            response.getValueJson();

            if(!response.getValueJson().isEmpty()){
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

            System.out.println("gRPC Server Response(" + response.getStatus() + ")\n" + (responseObject != null ? responseObject.toString() : "null"));

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
}
