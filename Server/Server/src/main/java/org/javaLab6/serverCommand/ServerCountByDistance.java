package org.javaLab6.serverCommand;

import org.javaLab6.collection.CustomCollection;
import org.javaLab6.utils.ClientServer.ResponseStatus;
import org.javaLab6.utils.ClientServer.ServerResponse;
import org.javaLab6.utils.command.CommandArgumentList;

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
