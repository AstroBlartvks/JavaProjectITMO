package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.grpc.ClientServerActionMessage;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLabServer.collection.CustomCollection;

public class ServerShow extends ServerCommand {
    private final CustomCollection collection;

    public ServerShow(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(ClientServerActionMessage args) {
        return new ServerResponse(ResponseStatus.TEXT, this.collection.getRoutesDescriptions());
    }
}
