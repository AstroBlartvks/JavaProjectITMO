package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;

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
