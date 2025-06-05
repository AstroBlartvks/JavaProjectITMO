
package AstroLabServer.grpc;

import AstroLab.auth.ConnectionType;
import AstroLab.auth.UserDTO;
import AstroLab.grpc.*;
import AstroLabServer.auth.AuthService;
import AstroLabServer.auth.AuthStates;
import AstroLabServer.auth.JwtUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AstroAuthServiceImpl extends AstroAuthServiceGrpc.AstroAuthServiceImplBase {
    private static final Logger LOGGER = LogManager.getLogger(AstroAuthServiceImpl.class);
    private final AuthService authService;

    public AstroAuthServiceImpl(AuthService authService) {
        this.authService = authService;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public void authenticate(UserDTOProto request, StreamObserver<AuthResponseProto> responseObserver) {
        UserDTO dto = new UserDTO(
                request.getLogin(),
                request.getPassword(),
                "",
                request.getConnectionType() == ConnectionTypeProto.LOGIN ? ConnectionType.LOGIN : ConnectionType.REGISTER
        );

        LOGGER.info("gRPC Auth request for: {}", dto.getLogin());

        try {
            AuthStates authStates = dto.getConnectionType() == ConnectionType.LOGIN ? authService.login(dto) : authService.register(dto);
            AuthResponseProto.Builder responseBuilder = AuthResponseProto.newBuilder();

            if (authStates.isState()) {
                String token = JwtUtils.generateToken(dto.getLogin());
                responseBuilder.setStatus(ResponseStatusProto.OK)
                        .setMessage(authStates.getMessage())
                        .setToken(token);
            } else {
                responseBuilder.setStatus(ResponseStatusProto.FORBIDDEN)
                        .setMessage(authStates.getMessage());
            }
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            LOGGER.error("Auth processing error", e);
            AuthResponseProto response = AuthResponseProto.newBuilder()
                    .setStatus(ResponseStatusProto.EXCEPTION)
                    .setMessage("Server error during authentication: " + e.getMessage())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}