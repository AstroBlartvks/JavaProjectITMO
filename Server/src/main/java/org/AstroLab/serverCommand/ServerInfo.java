package org.AstroLab.serverCommand;

import org.AstroLab.collection.CustomCollection;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;

public class ServerInfo extends ServerCommand{
    private final CustomCollection collection;

    public ServerInfo(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) {
        return new ServerResponse(ResponseStatus.TEXT, this.collection.toString());
    }
}
