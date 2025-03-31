package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.model.Route;
import org.javaLab5.model.CreateRouteDTO;

import java.util.Date;

public class ServerAdd extends ServerCommand{
    private final CustomCollection collection;

    public ServerAdd(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) {
        CreateRouteDTO routeDTO = (CreateRouteDTO) args.getSecondArgument().getValue();
        Route newRoute = new Route();

        newRoute.setId(collection.getNewID());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(routeDTO);
        collection.addElement(newRoute);

        return new ServerResponse(ResponseStatus.OK, "Route{id="+newRoute.getId()+",name="+newRoute.getName()+"} successfully added ");
    }
}
