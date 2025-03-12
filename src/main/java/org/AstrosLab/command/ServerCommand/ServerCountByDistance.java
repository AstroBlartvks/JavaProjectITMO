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

        String argument = args.getFirstArgument().getValue().toString();
        double distance;

        try {
            distance = Double.parseDouble(args.getFirstArgument().getValue().toString());
        } catch (NumberFormatException e){
            throw new Exception("argument must be capable of being converted to 'Double', but '" + argument + "' cannot be transformed");
        }

        int counter = collection.countByDistance(distance);
        return new ServerResponse(ResponseStatus.DATA, String.valueOf(counter));
    }
}
