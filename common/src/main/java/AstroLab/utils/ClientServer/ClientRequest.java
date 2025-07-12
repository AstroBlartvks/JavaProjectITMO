package AstroLab.utils.ClientServer;

import AstroLab.actions.components.ClientServerAction;
import AstroLab.grpc.ClientServerActionMessage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequest {
    @JsonProperty("request")
    ClientServerActionMessage request;
    @JsonProperty("token")
    private String token;

    @JsonCreator
    public ClientRequest(
            @JsonProperty("request") ClientServerActionMessage request
    ) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "Request=" + request.toString() + "}";
    }

}
