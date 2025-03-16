package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.model.Coordinates;
import org.javaLab5.model.Location;
import org.javaLab5.model.Route;

import java.util.Date;

public class ServerUpdate extends ServerCommand{
    private final CustomCollection collection;

    public ServerUpdate(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) throws Exception {
        CommandArgumentList routeElements = args.getElementArguments();
        int routeId = (int)args.getFirstArgument().getValue();

        if (!this.collection.containsID(routeId)){
            throw new Exception("There is no such 'Route' with 'id'="+routeId);
        }

        Route newRoute = new Route();
        newRoute.setId(routeId);

        Date updatedDate = new Date();
        newRoute.setCreationDate(updatedDate);

        newRoute.setName((String)routeElements.getArgumentByIndex(0).getValue());
        newRoute.setCoordinates((Coordinates)routeElements.getArgumentByIndex(1).getValue());
        newRoute.setFrom((Location) routeElements.getArgumentByIndex(2).getValue());
        newRoute.setTo((Location)routeElements.getArgumentByIndex(3).getValue());
        newRoute.setDistance((Double)routeElements.getArgumentByIndex(4).getValue());
        this.collection.updateElement(newRoute);

        return new ServerResponse(ResponseStatus.OK, "Route{id="+newRoute.getId()+",name="+newRoute.getName()+"} successfully updated");
    }
}
