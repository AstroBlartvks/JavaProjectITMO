package org.javaLab5.command.serverCommand;

import org.javaLab5.command.CommandArgumentList;

public class ServerExit extends ServerCommand{
    @Override
    public ServerResponse execute(CommandArgumentList args){
        return new ServerResponse(ResponseStatus.EXIT, null);
    }
}
