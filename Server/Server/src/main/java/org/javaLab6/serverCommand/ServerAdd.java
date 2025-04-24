package org.javaLab6.serverCommand;

import org.javaLab6.collection.CustomCollection;
import org.javaLab6.utils.ClientServer.ResponseStatus;
import org.javaLab6.utils.ClientServer.ServerResponse;
import org.javaLab6.utils.command.CommandArgumentList;
import org.javaLab6.utils.model.Route;
import org.javaLab6.utils.model.CreateRouteDTO;

import java.util.Date;
import java.util.LinkedHashMap;

public class ServerAdd extends ServerCommand{
    private final CustomCollection collection;

    public ServerAdd(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) {
        CreateRouteDTO routeDTO = CreateRouteDTO.fromMap((LinkedHashMap<String, Object>) args.getLastArgument().getValue());
        Route newRoute = new Route();

        newRoute.setId(collection.getNewID());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(routeDTO);
        if (collection.isExist(newRoute)){
            return new ServerResponse(ResponseStatus.EXCEPTION, "This Route is already exist");
        }
        collection.addElement(newRoute);

        return new ServerResponse(ResponseStatus.OK, "Route{id="+newRoute.getId()+",name="+newRoute.getName()+"} successfully added ");
    }
}
