package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLabServer.collection.CustomCollection;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;

public class ServerClear extends ServerCommand{
    private final CustomCollection collection;

    public ServerClear(CustomCollection collection) {
        this.collection = collection;
    }
    @Override
    public ServerResponse execute(Action args){
        this.collection.clear();
        return new ServerResponse(ResponseStatus.OK, "Cleared successfully!");
    }
}
