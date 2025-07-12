package AstroLabServer.auth;

import lombok.Getter;

@Getter
public enum AuthStates {
    LOGGED("You are logged in!", true),
    REGISTERED("You are registered!", true),
    NOT_LOGGED("You are NOT logged, check password or login!", false),
    CANT_LOGIN_ACCOUNT_NOT_REGISTERED("You are NOT logged, because account doesn't exist!", false),
    CANT_REGISTER_ACCOUNT_EXIST("You are NOT registered, because account with this login exist!", false);

    private final String message;
    private final boolean state;

    AuthStates(String message, boolean isOK) {
        this.message = message;
        this.state = isOK;
    }
}
