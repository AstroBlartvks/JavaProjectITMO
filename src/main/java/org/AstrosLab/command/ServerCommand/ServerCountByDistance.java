package org.AstrosLab.command.ServerCommand;

import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.command.CommandArgumentList;

public class ServerCountByDistance extends ServerCommand{
    private final CustomCollection collection;

    public ServerCountByDistance(CustomCollection collection){
        this.collection = collection;
    }
    @Override
    public ServerResponse execute(CommandArgumentList args) throws Exception {
        if (args.length() != 2){
            throw new Exception("count_by_distance must have 2 arguments, not '"+args.length()+"'");
        }

        int counter = collection.countByDistance((double)args.getFirstArgument().getValue());
        return new ServerResponse(ResponseStatus.DATA, String.valueOf(counter));
    }
}
