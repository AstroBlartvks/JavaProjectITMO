package org.AstrosLab.command.ServerCommand;

import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.command.CommandArgumentList;
import org.AstrosLab.model.Coordinates;
import org.AstrosLab.model.Location;
import org.AstrosLab.model.Route;

import java.util.Date;
import java.util.Optional;

public class ServerAddIfMin extends ServerCommand {
    private CustomCollection collection;
    public ServerAddIfMin(CustomCollection collection) {
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

        Optional<Route> minRoute = this.collection.getCollection().stream().min(Route::compareTo);
        if (minRoute.isEmpty() || newRoute.compareTo(minRoute.get()) < 0){
            this.collection.addElement(newRoute);
            return new ServerResponse(ResponseStatus.OK, "Route was added with id="+newRoute.getId());
        }
        return new ServerResponse(ResponseStatus.OK, "Route was not added");
    }
}
