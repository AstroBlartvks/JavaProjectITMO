package org.AstroLabServer.serverCommand;

import org.AstroLabServer.collection.CustomCollection;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgument;
import org.AstroLab.utils.command.CommandArgumentList;

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
