package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLabServer.collection.CustomCollection;

public class ServerClear extends ServerCommand {
    private final CustomCollection collection;

    public ServerClear(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(Action args) {
        this.collection.clear();
        return new ServerResponse(ResponseStatus.OK, "Cleared successfully!");
    }
}
