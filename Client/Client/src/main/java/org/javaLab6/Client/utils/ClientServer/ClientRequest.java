package org.javaLab6.Client.utils.ClientServer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.javaLab6.Client.utils.command.CommandArgument;

import java.util.List;

@Getter
@Setter
public class ClientRequest {
    @JsonProperty("state")
    ClientStatus state;
    @JsonProperty("request")
    List<CommandArgument> request;

    @JsonCreator
    public ClientRequest(
            @JsonProperty("state") ClientStatus state,
            @JsonProperty("request") List<CommandArgument> request
    ) {
        this.state = state;
        this.request = request;
    }

    @Override
    public String toString() {
        return "RequestStatus=" + state.toString() + "\nRequest=" +request.toString() + "}";
    }
}
