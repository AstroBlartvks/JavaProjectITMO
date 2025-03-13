package org.AstrosLab.command.ServerCommand;

import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.command.CommandArgumentList;
import org.AstrosLab.model.Coordinates;
import org.AstrosLab.model.Location;
import org.AstrosLab.model.Route;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerRemoveGreater extends ServerCommand{
    private final CustomCollection collection;

    public ServerRemoveGreater(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) throws Exception {
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
}
