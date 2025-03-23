package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.model.Route;
import org.javaLab5.model.RouteDataTransferObject;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerRemoveGreater extends ServerCommand{
    private final CustomCollection collection;

    public ServerRemoveGreater(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) {
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

        return new ServerResponse(ResponseStatus.OK, response);
    }

    private Route handleNewRoute(CommandArgumentList args) {
        RouteDataTransferObject routeDTO = (RouteDataTransferObject) args.getSecondArgument().getValue();
        Route newRoute = new Route();

        newRoute.setId(collection.getNewID());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(routeDTO);

        return newRoute;
    }
}
