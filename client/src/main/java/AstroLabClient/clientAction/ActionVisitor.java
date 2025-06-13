package AstroLabClient.clientAction;

import AstroLab.actions.components.ClientAction;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.actions.utils.ActionVisitable;
import AstroLab.grpc.*;
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

    @Override
    public void visit(ClientAction clientAction) throws Exception{
        clientAction.executeLocally();
    }

    @Override
    public void visit(ClientServerAction clientServerAction) throws ServerException {
        if (jwtToken == null) {
            throw new IllegalStateException("Not authenticated. Cannot send command.");
        }

        ClientServerActionMessage clientServerActionMessage = clientServerAction.toProtobuf();

        ClientRequestMessage request = ClientRequestMessage.newBuilder()
                .setAction(clientServerActionMessage)
                .build();

        System.out.println("Sending gRPC command...");
        try {
            int durationBeforeDeath = 5;
            ServerResponseMessage response = commandStub.withDeadlineAfter(durationBeforeDeath, TimeUnit.SECONDS)
                    .executeCommand(request);

            String message = response.getServerMessage();

            System.out.println("gRPC Server Response(" + response.getStatus() + ")\n" + message);

            if (response.getStatus() == ResponseStatusMessage.EXCEPTION || response.getStatus() == ResponseStatusMessage.UNAUTHORIZED) {
                System.err.println("Server error: " + message);
            }

        } catch (StatusRuntimeException e) {
            System.err.println("gRPC Command request failed: " + e.getStatus());
            if (e.getStatus().getCode() == io.grpc.Status.Code.UNAVAILABLE) {
                throw new ServerException("Server unavailable!");
            }
        }
    }
}
