package org.AstroLabServer.serverCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLabServer.collection.CustomCollection;

public class ServerInfo extends ServerCommand{
    private final CustomCollection collection;

    public ServerInfo(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(Action args) {
        return new ServerResponse(ResponseStatus.TEXT, this.collection.toString());
    }
}
