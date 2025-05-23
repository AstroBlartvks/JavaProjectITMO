package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionRemoveById;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLabServer.collection.CustomCollection;

public class ServerRemoveById extends ServerCommand {
    private final CustomCollection collection;

    public ServerRemoveById(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(Action args) throws Exception {
        ActionRemoveById action = (ActionRemoveById) args;

        int id = action.getId();

        if (!this.collection.containsId(id)) {
            throw new Exception("There is no 'id'=" + id + " in the collection");
        }

        this.collection.removeById(id);
        return new ServerResponse(ResponseStatus.OK, "Route deleted successfully");
    }
}
