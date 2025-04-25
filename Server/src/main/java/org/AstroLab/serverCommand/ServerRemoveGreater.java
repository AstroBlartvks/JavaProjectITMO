package org.AstroLab.serverCommand;

import org.AstroLab.collection.CustomCollection;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLab.utils.model.Route;
import org.AstroLab.utils.model.CreateRouteDTO;

import java.util.Date;
import java.util.LinkedHashMap;
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
        CreateRouteDTO routeDTO = CreateRouteDTO.fromMap((LinkedHashMap<String, Object>) args.getLastArgument().getValue());
        Route newRoute = new Route();

        newRoute.setId(collection.getNewID());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(routeDTO);

        return newRoute;
    }
}
