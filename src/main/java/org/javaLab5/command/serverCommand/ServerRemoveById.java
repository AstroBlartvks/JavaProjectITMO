package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;

public class ServerRemoveById extends ServerCommand {
    private final CustomCollection collection;

    public ServerRemoveById(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) throws Exception {
        int id = (int)args.getFirstArgument().getValue();

        if (!this.collection.containsID(id)){
            throw new Exception("There is no 'id'="+id+" in the collection");
        }

        this.collection.removeByID(id);
        return new ServerResponse(ResponseStatus.OK, "Route deleted successfully");
    }
}
