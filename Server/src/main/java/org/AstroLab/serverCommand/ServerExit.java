package org.AstroLab.serverCommand;

import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;

public class ServerExit extends ServerCommand{
    @Override
    public ServerResponse execute(CommandArgumentList args){
        return new ServerResponse(ResponseStatus.EXIT, null);
    }
}
