package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;

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
