package AstroLabServer.grpc;

import AstroLab.grpc.*;
import AstroLab.utils.ClientServer.ClientRequest;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLabServer.auth.JwtServerInterceptor;
import io.grpc.stub.*;
import AstroLabServer.onlyServerCommand.ServerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AstroCommandServiceImpl extends AstroCommandServiceGrpc.AstroCommandServiceImplBase {
    private static final Logger LOGGER = LogManager.getLogger(AstroCommandServiceImpl.class);
    private final ServerUtils serverUtils;

    public AstroCommandServiceImpl(ServerUtils serverUtils) {
        this.serverUtils = serverUtils;
    }

    @Override
    public void executeCommand(ClientRequestMessage request, StreamObserver<ServerResponseMessage> responseObserver) {
        ServerResponseMessage.Builder responseBuilder = ServerResponseMessage.newBuilder();

        try {
            ClientServerActionMessage action = request.getAction();

            LOGGER.info("Execution command: {}", action.getActionName());

            ClientRequest clientRequestPojo = new ClientRequest(action);

            ServerResponse serverPojoResponse = serverUtils.executeCommandSafely(clientRequestPojo);

            responseBuilder.setStatus(mapToProtoStatus(serverPojoResponse.getStatus()))
                    .setServerMessage(serverPojoResponse.getValue().toString());
        } catch (Exception e) {
            LOGGER.error("Command processing error", e);
            responseBuilder.setStatus(ResponseStatusMessage.EXCEPTION)
                    .setServerMessage("Server error: " + e.getMessage());
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    private ResponseStatusMessage mapToProtoStatus(ResponseStatus status) {
        return switch (status) {
            case OK -> ResponseStatusMessage.OK;
            case TEXT -> ResponseStatusMessage.TEXT;
            case DATA -> ResponseStatusMessage.DATA;
            case FORBIDDEN -> ResponseStatusMessage.FORBIDDEN;
            case UNAUTHORIZED -> ResponseStatusMessage.UNAUTHORIZED;
            case EXCEPTION -> ResponseStatusMessage.EXCEPTION;
        };
    }
}