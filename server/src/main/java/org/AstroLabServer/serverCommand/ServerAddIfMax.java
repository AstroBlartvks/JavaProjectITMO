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
import java.util.Optional;

public class ServerAddIfMax extends ServerCommand{
    private final CustomCollection collection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ServerAddIfMax(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) {
        CreateRouteDTO routeDTO = objectMapper.convertValue(args.getLastArgument().getValue(), CreateRouteDTO.class);
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
