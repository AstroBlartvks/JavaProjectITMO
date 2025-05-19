package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionCountGreaterThanDistance;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.command.CommandArgument;
import AstroLabServer.collection.CustomCollection;

import java.sql.Connection;

public class ServerCountGreaterThanDistance extends ServerCommand {
    private final CustomCollection collection;
    private final Connection connection;

    public ServerCountGreaterThanDistance(CustomCollection collection, Connection connection) {
        this.collection = collection;
        this.connection = connection;
    }

    @Override
    public ServerResponse execute(Action args) {
        ActionCountGreaterThanDistance action = (ActionCountGreaterThanDistance) args;
        int count = this.collection.countGreaterThanDistance(action.getDistance());
        return new ServerResponse(ResponseStatus.OK, new CommandArgument(count));
    }
}
