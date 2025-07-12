package AstroLabServer.grpc;

import AstroLab.auth.User;
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
    public void login(LoginRequest request, StreamObserver<AuthResponseMessage> responseObserver) {
        User user = new User(request.getLogin(), request.getPassword(), "");
        LOGGER.info("gRPC Login request for: {}", user.getLogin());

        processAuth(user, responseObserver, true);
    }

    @Override
    public void register(RegisterRequest request, StreamObserver<AuthResponseMessage> responseObserver) {
        User user = new User(request.getLogin(), request.getPassword(), "");
        LOGGER.info("gRPC Register request for: {}", user.getLogin());

        processAuth(user, responseObserver, false);
    }

    private void processAuth(User user, StreamObserver<AuthResponseMessage> responseObserver, boolean isLogin) {
        try {
            AuthStates authStates = isLogin ?
                    authService.login(user) :
                    authService.register(user);

            AuthResponseMessage.Builder responseBuilder = AuthResponseMessage.newBuilder();

            if (authStates.isState()) {
                String token = JwtUtils.generateToken(user);
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