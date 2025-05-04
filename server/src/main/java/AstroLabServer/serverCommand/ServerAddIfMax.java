package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionAddIfMax;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;
import java.util.Date;
import java.util.Optional;

public class ServerAddIfMax extends ServerCommand {
    private final CustomCollection collection;

    public ServerAddIfMax(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(Action args) {
        ActionAddIfMax action = (ActionAddIfMax) args;
        Route newRoute = new Route();

        newRoute.setId(collection.getNewId());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(action.getCreateRouteDto());

        Optional<Route> maxRoute = this.collection.getCollection().stream().max(Route::compareTo);
        if (maxRoute.isEmpty() || newRoute.compareTo(maxRoute.get()) > 0) {
            this.collection.addElement(newRoute);
            return new ServerResponse(ResponseStatus.OK, "Route was added with id=" + newRoute.getId());
        }
        return new ServerResponse(ResponseStatus.OK, "Route was not added");
    }
}
