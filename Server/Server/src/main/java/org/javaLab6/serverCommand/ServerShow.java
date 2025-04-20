package org.javaLab6.serverCommand;

import org.javaLab6.collection.CustomCollection;
import org.javaLab6.utils.ClientServer.ResponseStatus;
import org.javaLab6.utils.ClientServer.ServerResponse;
import org.javaLab6.utils.command.CommandArgumentList;

public class ServerShow extends ServerCommand {
    private final CustomCollection collection;

    public ServerShow(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) {
        return new ServerResponse(ResponseStatus.TEXT, this.collection.getRoutesDescriptions());
    }
}
