package AstroLabServer.grpc;

import AstroLab.actions.components.ClientServerAction;
import AstroLab.grpc.AstroCommandServiceGrpc;
import AstroLab.grpc.ClientRequestProto;
import AstroLab.grpc.ResponseStatusProto;
import AstroLab.grpc.ServerResponseProto;
import AstroLab.utils.ClientServer.ClientRequest;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLabServer.auth.JwtUtils;
import io.grpc.stub.*;
import AstroLabServer.onlyServerCommand.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AstroCommandServiceImpl extends AstroCommandServiceGrpc.AstroCommandServiceImplBase {
    private static final Logger LOGGER = LogManager.getLogger(AstroCommandServiceImpl.class);
    private final ServerUtils serverUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AstroCommandServiceImpl(ServerUtils serverUtils) {
        this.serverUtils = serverUtils;
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public void executeCommand(ClientRequestProto request, StreamObserver<ServerResponseProto> responseObserver) {
        LOGGER.info("gRPC Command request with token: {}", request.getToken());
        ServerResponseProto.Builder responseBuilder = ServerResponseProto.newBuilder();

        try{
            if (!JwtUtils.validateToken(request.getToken())) {
                responseBuilder.setStatus(ResponseStatusProto.UNAUTHORIZED).setValueJson(objectMapper.writeValueAsString("Invalid token"));
                responseObserver.onNext(responseBuilder.build());
                responseObserver.onCompleted();
                return;
            }
        } catch (Exception e) {
            responseBuilder.setStatus(ResponseStatusProto.UNAUTHORIZED).setValueJson("Invalid token"); //!
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        }

        String username = JwtUtils.getUsernameFromToken(request.getToken());
        LOGGER.info("Request from user {}: action_json='{}'", username, request.getActionJson());

        try {
            ClientServerAction action = objectMapper.readValue(request.getActionJson(), ClientServerAction.class); // Or specific Action type if known
            ClientRequest clientRequestPojo = new ClientRequest(action);
            clientRequestPojo.setToken(request.getToken());

            ServerResponse serverPojoResponse = serverUtils.executeCommandSafely(clientRequestPojo);

            responseBuilder.setStatus(mapToProtoStatus(serverPojoResponse.getStatus()))
                    .setValueJson(objectMapper.writeValueAsString(serverPojoResponse.getValue()));

        } catch (JsonProcessingException e) {
            LOGGER.error("JSON processing error for command", e);
            try {
                responseBuilder.setStatus(ResponseStatusProto.EXCEPTION)
                        .setValueJson(objectMapper.writeValueAsString("Client request JSON error: " + e.getMessage()));
            } catch (JsonProcessingException ignored) {}
        }
        catch (Exception e) {
            LOGGER.error("Command processing error", e);
            try {
                responseBuilder.setStatus(ResponseStatusProto.EXCEPTION)
                        .setValueJson(objectMapper.writeValueAsString("Server error: " + e.getMessage()));
            } catch (JsonProcessingException ignored) {}
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    private ResponseStatusProto mapToProtoStatus(ResponseStatus status) {
        return switch (status) {
            case OK -> ResponseStatusProto.OK;
            case TEXT -> ResponseStatusProto.TEXT;
            case DATA -> ResponseStatusProto.DATA;
            case FORBIDDEN -> ResponseStatusProto.FORBIDDEN;
            case UNAUTHORIZED -> ResponseStatusProto.UNAUTHORIZED;
            case EXCEPTION -> ResponseStatusProto.EXCEPTION;
        };
    }
}