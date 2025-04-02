package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;

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
