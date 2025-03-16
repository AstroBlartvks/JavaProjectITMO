package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.model.Coordinates;
import org.javaLab5.model.Location;
import org.javaLab5.model.Route;

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
        Route newRoute = getRoute(args);

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

    private Route getRoute(CommandArgumentList args) {
        CommandArgumentList routeElements = args.getElementArguments();
        Route newRoute = new Route();

        int id = this.collection.getNewID();
        Date date = new Date();

        newRoute.setId(id);
        newRoute.setCreationDate(date);

        newRoute.setName((String)routeElements.getArgumentByIndex(0).getValue());
        newRoute.setCoordinates((Coordinates)routeElements.getArgumentByIndex(1).getValue());
        newRoute.setFrom((Location) routeElements.getArgumentByIndex(2).getValue());
        newRoute.setTo((Location)routeElements.getArgumentByIndex(3).getValue());
        newRoute.setDistance((Double)routeElements.getArgumentByIndex(4).getValue());
        return newRoute;
    }
}
