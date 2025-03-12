package org.AstrosLab.command.ServerCommand;

import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.command.CommandArgumentList;

public class ServerClear extends ServerCommand{
    private final CustomCollection collection;

    public ServerClear(CustomCollection collection) {
        this.collection = collection;
    }
    @Override
    public ServerResponse execute(CommandArgumentList args) throws Exception {
        this.collection.clear();
        return new ServerResponse(ResponseStatus.OK, null);
    }
}
