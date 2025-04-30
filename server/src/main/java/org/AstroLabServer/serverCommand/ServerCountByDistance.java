package org.AstroLabServer.serverCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionCountByDistance;
import org.AstroLabServer.collection.CustomCollection;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;

public class ServerCountByDistance extends ServerCommand{
    private final CustomCollection collection;

    public ServerCountByDistance(CustomCollection collection){
        this.collection = collection;
    }
    @Override
    public ServerResponse execute(Action args) {
        ActionCountByDistance action = (ActionCountByDistance) args;
        int counter = collection.countByDistance(action.getDistance());
        return new ServerResponse(ResponseStatus.DATA, String.valueOf(counter));
    }
}
