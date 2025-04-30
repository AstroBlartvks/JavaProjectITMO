package org.AstroLabServer.serverCommand;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.AstroLabServer.collection.CustomCollection;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLab.utils.model.Route;
import org.AstroLab.utils.model.CreateRouteDTO;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;

public class ServerAdd extends ServerCommand{
    private final CustomCollection collection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ServerAdd(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) {
        CreateRouteDTO routeDTO = objectMapper.convertValue(args.getLastArgument().getValue(), CreateRouteDTO.class);
        Route newRoute = new Route();

        newRoute.setId(collection.getNewID());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(routeDTO);
        collection.addElement(newRoute);

        return new ServerResponse(ResponseStatus.OK, "Route{id="+newRoute.getId()+",name="+newRoute.getName()+"} successfully added ");
    }
}
