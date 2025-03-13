package org.AstrosLab.command.ServerCommand;

import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.command.CommandArgument;
import org.AstrosLab.command.CommandArgumentList;

public class ServerCountGreaterThanDistance extends ServerCommand{
    private final CustomCollection collection;

    public ServerCountGreaterThanDistance(CustomCollection collection) {
        this.collection = collection;
    }
    @Override
    public ServerResponse execute(CommandArgumentList args) throws Exception {
        int count = this.collection.countGreaterThanDistance((double)args.getFirstArgument().getValue());
        return new ServerResponse(ResponseStatus.OK, new CommandArgument(count));
    }
}
