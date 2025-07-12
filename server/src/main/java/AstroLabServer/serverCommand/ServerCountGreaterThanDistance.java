package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionCountGreaterThanDistance;
import AstroLab.grpc.ClientServerActionMessage;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.command.CommandArgument;
import AstroLab.utils.model.CreateRouteDto;
import AstroLabServer.collection.CustomCollection;

import java.sql.Connection;

public class ServerCountGreaterThanDistance extends ServerCommand {
    private final CustomCollection collection;

    public ServerCountGreaterThanDistance(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(ClientServerActionMessage args) {
        int count = this.collection.countGreaterThanDistance(args.getDistance());
        return new ServerResponse(ResponseStatus.OK, new CommandArgument(count));
    }
}
