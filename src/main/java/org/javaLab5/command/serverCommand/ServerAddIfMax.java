package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.model.Coordinates;
import org.javaLab5.model.Location;
import org.javaLab5.model.Route;

import java.util.Date;
import java.util.Optional;

public class ServerAddIfMax extends ServerCommand{
    private final CustomCollection collection;

    public ServerAddIfMax(CustomCollection collection){
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

        Optional<Route> maxRoute = this.collection.getCollection().stream().max(Route::compareTo);
        if (maxRoute.isEmpty() || newRoute.compareTo(maxRoute.get()) > 0){
            this.collection.addElement(newRoute);
            return new ServerResponse(ResponseStatus.OK, "Route was added with id="+newRoute.getId());
        }
        return new ServerResponse(ResponseStatus.OK, "Route was not added");
    }
}
