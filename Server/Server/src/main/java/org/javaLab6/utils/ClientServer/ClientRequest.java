package org.javaLab6.utils.ClientServer;

import lombok.Getter;
import org.javaLab6.utils.command.CommandArgumentList;

@Getter
public class ClientRequest {
    ClientStatus state;
    CommandArgumentList request;

    public ClientRequest(ClientStatus state, CommandArgumentList command){
        this.state = state;
        this.request = command;
    }

    @Override
    public String toString() {
        return "RequestStatus=" + state.toString() + "\nRequest=" +request.toString() + "}";
    }
}
