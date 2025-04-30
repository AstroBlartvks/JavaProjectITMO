package org.AstroLabServer.serverCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionAddIfMin;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.model.Route;
import org.AstroLabServer.collection.CustomCollection;

import java.util.Date;
import java.util.Optional;

public class ServerAddIfMin extends ServerCommand {
    private final CustomCollection collection;

    public ServerAddIfMin(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(Action args) {
        ActionAddIfMin action = (ActionAddIfMin) args;

        Route newRoute = new Route();

        newRoute.setId(collection.getNewID());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(action.getCreateRouteDTO());

        Optional<Route> minRoute = this.collection.getCollection().stream().min(Route::compareTo);
        if (minRoute.isEmpty() || newRoute.compareTo(minRoute.get()) < 0){
            this.collection.addElement(newRoute);
            return new ServerResponse(ResponseStatus.OK, "Route was added with id="+newRoute.getId());
        }
        return new ServerResponse(ResponseStatus.OK, "Route was not added");
    }
}
