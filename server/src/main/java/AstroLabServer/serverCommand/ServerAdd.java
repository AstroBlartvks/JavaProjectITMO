package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionAdd;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;
import java.util.Date;

public class ServerAdd extends ServerCommand {
    private final CustomCollection collection;

    public ServerAdd(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(Action args) {
        ActionAdd action = (ActionAdd) args;
        Route newRoute = new Route();

        newRoute.setId(collection.getNewId());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(action.getCreateRouteDto());
        collection.addElement(newRoute);

        return new ServerResponse(ResponseStatus.OK, "Route{id=" + newRoute.getId() +
                ",name=" + newRoute.getName() + "} successfully added ");
    }
}
