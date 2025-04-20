package org.javaLab6.serverCommand;

import org.javaLab6.utils.ClientServer.ResponseStatus;
import org.javaLab6.utils.ClientServer.ServerResponse;
import org.javaLab6.utils.command.CommandArgumentList;

public class ServerExit extends ServerCommand{
    @Override
    public ServerResponse execute(CommandArgumentList args){
        return new ServerResponse(ResponseStatus.EXIT, null);
    }
}
