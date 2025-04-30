package org.AstroLabServer.serverCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionRemoveById;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLabServer.collection.CustomCollection;

public class ServerRemoveById extends ServerCommand {
    private final CustomCollection collection;

    public ServerRemoveById(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(Action args) throws Exception {
        ActionRemoveById action = (ActionRemoveById) args;

        int id = action.getId();

        if (!this.collection.containsID(id)){
            throw new Exception("There is no 'id'="+id+" in the collection");
        }

        this.collection.removeByID(id);
        return new ServerResponse(ResponseStatus.OK, "Route deleted successfully");
    }
}
