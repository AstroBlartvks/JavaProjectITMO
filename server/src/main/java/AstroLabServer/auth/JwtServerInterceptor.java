package AstroLabServer.auth;

import io.grpc.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JwtServerInterceptor implements ServerInterceptor {
    private static final Logger LOGGER = LogManager.getLogger(JwtServerInterceptor.class);
    private static final Metadata.Key<String> TOKEN_HEADER_KEY =
            Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        String token = headers.get(TOKEN_HEADER_KEY);
        if (token == null) {
            call.close(Status.UNAUTHENTICATED.withDescription("Token missing"), headers);
            return new ServerCall.Listener<>() {};
        }

        try {
            if (!JwtUtils.validateToken(token)) {
                call.close(Status.UNAUTHENTICATED.withDescription("Invalid token"), headers);
                return new ServerCall.Listener<>() {};
            }
            return next.startCall(call, headers);
        } catch (Exception e) {
            LOGGER.error("Token validation error", e);
            call.close(Status.UNAUTHENTICATED.withDescription("Token validation failed"), headers);
            return new ServerCall.Listener<>() {};
        }
    }
}