package org.AstroLabServer.serverCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionCountGreaterThanDistance;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgument;
import org.AstroLabServer.collection.CustomCollection;

public class ServerCountGreaterThanDistance extends ServerCommand{
    private final CustomCollection collection;

    public ServerCountGreaterThanDistance(CustomCollection collection) {
        this.collection = collection;
    }
    @Override
    public ServerResponse execute(Action args){
        ActionCountGreaterThanDistance action = (ActionCountGreaterThanDistance) args;
        int count = this.collection.countGreaterThanDistance(action.getDistance());
        return new ServerResponse(ResponseStatus.OK, new CommandArgument(count));
    }
}
