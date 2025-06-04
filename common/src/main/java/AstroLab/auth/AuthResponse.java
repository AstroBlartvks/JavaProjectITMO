package AstroLab.auth;

import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse extends ServerResponse {
    @JsonProperty("token")
    private String token;

    @JsonCreator
    public AuthResponse(@JsonProperty("status") ResponseStatus status,
                        @JsonProperty("value") String message,
                        @JsonProperty("token") String token) {
        super(status, message);
        this.token = token;
    }
}

