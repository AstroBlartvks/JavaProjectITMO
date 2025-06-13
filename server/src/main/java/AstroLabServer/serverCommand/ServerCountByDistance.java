package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionCountByDistance;
import AstroLab.grpc.ClientServerActionMessage;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.CreateRouteDto;
import AstroLabServer.collection.CustomCollection;

public class ServerCountByDistance extends ServerCommand {
    private final CustomCollection collection;

    public ServerCountByDistance(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(ClientServerActionMessage args) {
        int counter = collection.countByDistance(args.getDistance());
        return new ServerResponse(ResponseStatus.DATA, String.valueOf(counter));
    }
}
