package org.javaLab6.serverCommand;

import org.javaLab6.collection.CustomCollection;
import org.javaLab6.utils.ClientServer.ResponseStatus;
import org.javaLab6.utils.ClientServer.ServerResponse;
import org.javaLab6.utils.command.CommandArgument;
import org.javaLab6.utils.command.CommandArgumentList;

public class ServerCountGreaterThanDistance extends ServerCommand{
    private final CustomCollection collection;

    public ServerCountGreaterThanDistance(CustomCollection collection) {
        this.collection = collection;
    }
    @Override
    public ServerResponse execute(CommandArgumentList args){
        int count = this.collection.countGreaterThanDistance((double)args.getFirstArgument().getValue());
        return new ServerResponse(ResponseStatus.OK, new CommandArgument(count));
    }
}
