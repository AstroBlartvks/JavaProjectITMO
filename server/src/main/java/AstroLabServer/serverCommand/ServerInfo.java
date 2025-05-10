package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLabServer.collection.CustomCollection;

public class ServerInfo extends ServerCommand {
    private final CustomCollection collection;

    public ServerInfo(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(Action args) {
        return new ServerResponse(ResponseStatus.TEXT, this.collection.toString());
    }
}
