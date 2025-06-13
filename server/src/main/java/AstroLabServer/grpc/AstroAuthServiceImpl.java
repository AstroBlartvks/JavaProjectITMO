
package AstroLabServer.grpc;

import AstroLab.auth.ConnectionType;
import AstroLab.auth.UserDTO;
import AstroLab.grpc.*;
import AstroLabServer.auth.AuthService;
import AstroLabServer.auth.AuthStates;
import AstroLabServer.auth.JwtUtils;
import io.grpc.stub.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AstroAuthServiceImpl extends AstroAuthServiceGrpc.AstroAuthServiceImplBase {
    private static final Logger LOGGER = LogManager.getLogger(AstroAuthServiceImpl.class);
    private final AuthService authService;

    public AstroAuthServiceImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void authenticate(UserDTOMessage request, StreamObserver<AuthResponseMessage> responseObserver) {
        UserDTO dto = new UserDTO(
                request.getLogin(),
                request.getPassword(),
                "",
                request.getConnectionType() == ConnectionTypeMessage.LOGIN ? ConnectionType.LOGIN : ConnectionType.REGISTER
        );

        LOGGER.info("gRPC Auth request for: {}", dto.getLogin());

        try {
            AuthStates authStates = dto.getConnectionType() == ConnectionType.LOGIN ? authService.login(dto) : authService.register(dto);
            AuthResponseMessage.Builder responseBuilder = AuthResponseMessage.newBuilder();

            if (authStates.isState()) {
                String token = JwtUtils.generateToken(dto);
                responseBuilder.setStatus(ResponseStatusMessage.OK)
                        .setMessage(authStates.getMessage())
                        .setToken(token);
            } else {
                responseBuilder.setStatus(ResponseStatusMessage.FORBIDDEN)
                        .setMessage(authStates.getMessage());
            }
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            LOGGER.error("Auth processing error", e);
            AuthResponseMessage response = AuthResponseMessage.newBuilder()
                    .setStatus(ResponseStatusMessage.EXCEPTION)
                    .setMessage("Server error during authentication: " + e.getMessage())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}