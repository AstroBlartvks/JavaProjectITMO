package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.model.Route;
import org.javaLab5.model.CreateRouteDTO;

import java.util.Date;
import java.util.Optional;

public class ServerAddIfMax extends ServerCommand{
    private final CustomCollection collection;

    public ServerAddIfMax(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) {
        CreateRouteDTO routeDTO = (CreateRouteDTO) args.getSecondArgument().getValue();
        Route newRoute = new Route();

        newRoute.setId(collection.getNewID());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(routeDTO);

        Optional<Route> maxRoute = this.collection.getCollection().stream().max(Route::compareTo);
        if (maxRoute.isEmpty() || newRoute.compareTo(maxRoute.get()) > 0){
            this.collection.addElement(newRoute);
            return new ServerResponse(ResponseStatus.OK, "Route was added with id="+newRoute.getId());
        }
        return new ServerResponse(ResponseStatus.OK, "Route was not added");
    }
}
