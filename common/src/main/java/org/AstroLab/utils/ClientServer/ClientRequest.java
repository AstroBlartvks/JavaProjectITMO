package org.AstroLab.utils.ClientServer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.components.Action;


@Getter
@Setter
public class ClientRequest {
    @JsonProperty("state")
    ClientStatus state;
    @JsonProperty("request")
    Action request;

    @JsonCreator
    public ClientRequest(
            @JsonProperty("state") ClientStatus state,
            @JsonProperty("request") Action request
    ) {
        this.state = state;
        this.request = request;
    }

    @Override
    public String toString() {
        return "RequestStatus=" + state.toString() + ";Request=" +request.toString() + "}";
    }
}
