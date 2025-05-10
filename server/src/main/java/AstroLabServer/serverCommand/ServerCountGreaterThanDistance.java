package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionCountGreaterThanDistance;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.command.CommandArgument;
import AstroLabServer.collection.CustomCollection;

public class ServerCountGreaterThanDistance extends ServerCommand {
    private final CustomCollection collection;

    public ServerCountGreaterThanDistance(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(Action args) {
        ActionCountGreaterThanDistance action = (ActionCountGreaterThanDistance) args;
        int count = this.collection.countGreaterThanDistance(action.getDistance());
        return new ServerResponse(ResponseStatus.OK, new CommandArgument(count));
    }
}
