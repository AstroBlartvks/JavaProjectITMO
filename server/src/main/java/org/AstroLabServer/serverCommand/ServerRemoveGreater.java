package org.AstroLabServer.serverCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionRemoveGreater;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.model.Route;
import org.AstroLabServer.collection.CustomCollection;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerRemoveGreater extends ServerCommand{
    private final CustomCollection collection;

    public ServerRemoveGreater(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(Action args) {
        Route newRoute = handleNewRoute(args);

        Set<Integer> greaterIds = this.collection.getCollection().stream()
                .filter(route -> route.compareTo(newRoute) > 0)
                .map(Route::getId)
                .collect(Collectors.toSet());

        StringBuilder response = new StringBuilder();

        for (int index : greaterIds){
            collection.removeByID(index);
            response.append("Route with id=").append(index).append(" was deleted!\n");
        }

        return new ServerResponse(ResponseStatus.OK, response.isEmpty() ?"Nothing removed" : response);
    }

    private Route handleNewRoute(Action args) {
        ActionRemoveGreater action = (ActionRemoveGreater) args;
        Route newRoute = new Route();

        newRoute.setId(collection.getNewID());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(action.getCreateRouteDTO());

        return newRoute;
    }
}
