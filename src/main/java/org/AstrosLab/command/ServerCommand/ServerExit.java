package org.AstrosLab.command.ServerCommand;

import org.AstrosLab.command.CommandArgumentList;

public class ServerExit extends ServerCommand{
    @Override
    public ServerResponse execute(CommandArgumentList args) throws Exception {
        return new ServerResponse(ResponseStatus.EXIT, null);
    }
}
