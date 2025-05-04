package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionCountByDistance;
import AstroLabServer.collection.CustomCollection;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;

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
