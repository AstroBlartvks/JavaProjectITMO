package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.model.Route;
import org.javaLab5.model.CreateRouteDTO;

import java.util.Date;

public class ServerUpdate extends ServerCommand{
    private final CustomCollection collection;

    public ServerUpdate(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) throws IllegalArgumentException {
        if (!this.collection.containsID((int) args.getFirstArgument().getValue())){
            throw new IllegalArgumentException("There is no such 'Route' with 'id'=" + args.getFirstArgument().getValue());
        }

        CreateRouteDTO routeDTO = (CreateRouteDTO) args.getSecondArgument().getValue();
        Route newRoute = new Route();

        newRoute.setId((int)args.getFirstArgument().getValue());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(routeDTO);
        this.collection.updateElement(newRoute);

        return new ServerResponse(ResponseStatus.OK, "Route{id="+newRoute.getId()+",name="+newRoute.getName()+"} successfully updated");
    }
}
