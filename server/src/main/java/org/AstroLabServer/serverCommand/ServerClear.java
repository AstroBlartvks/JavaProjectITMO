package org.AstroLabServer.serverCommand;

import org.AstroLabServer.collection.CustomCollection;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;

public class ServerClear extends ServerCommand{
    private final CustomCollection collection;

    public ServerClear(CustomCollection collection) {
        this.collection = collection;
    }
    @Override
    public ServerResponse execute(CommandArgumentList args){
        this.collection.clear();
        return new ServerResponse(ResponseStatus.OK, "Cleared successfully!");
    }
}
