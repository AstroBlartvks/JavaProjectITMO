package org.AstroLabServer.serverCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.AstroLabServer.collection.CustomCollection;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLab.utils.model.Route;
import org.AstroLab.utils.model.CreateRouteDTO;

import java.util.Date;
import java.util.LinkedHashMap;

public class ServerUpdate extends ServerCommand{
    private final CustomCollection collection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ServerUpdate(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) throws IllegalArgumentException {
        if (!this.collection.containsID((int) args.getFirstArgument().getValue())){
            throw new IllegalArgumentException("There is no such 'Route' with 'id'=" + args.getFirstArgument().getValue());
        }

        CreateRouteDTO routeDTO = objectMapper.convertValue(args.getLastArgument().getValue(), CreateRouteDTO.class);
        Route newRoute = new Route();

        newRoute.setId((int)args.getFirstArgument().getValue());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(routeDTO);

        this.collection.updateElement(newRoute);

        return new ServerResponse(ResponseStatus.OK, "Route{id="+newRoute.getId()+",name="+newRoute.getName()+"} successfully updated");
    }
}
