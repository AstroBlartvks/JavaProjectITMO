package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.model.Route;
import org.javaLab5.model.CreateRouteDTO;

import java.util.Date;
import java.util.Optional;

public class ServerAddIfMin extends ServerCommand {
    private final CustomCollection collection;
    public ServerAddIfMin(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) {
        CreateRouteDTO routeDTO = (CreateRouteDTO) args.getSecondArgument().getValue();
        Route newRoute = new Route();

        newRoute.setId(collection.getNewID());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(routeDTO);

        Optional<Route> minRoute = this.collection.getCollection().stream().min(Route::compareTo);
        if (minRoute.isEmpty() || newRoute.compareTo(minRoute.get()) < 0){
            this.collection.addElement(newRoute);
            return new ServerResponse(ResponseStatus.OK, "Route was added with id="+newRoute.getId());
        }
        return new ServerResponse(ResponseStatus.OK, "Route was not added");
    }
}
