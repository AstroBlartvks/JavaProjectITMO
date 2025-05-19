package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionUpdate;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;

import java.sql.Connection;
import java.util.Date;

public class ServerUpdate extends ServerCommand {
    private final CustomCollection collection;
    private final Connection connection;

    public ServerUpdate(CustomCollection collection, Connection connection) {
        this.collection = collection;
        this.connection = connection;
    }

    @Override
    public ServerResponse execute(Action args) throws IllegalArgumentException {
        ActionUpdate action = (ActionUpdate) args;

        if (!this.collection.containsId(action.getId())) {
            throw new IllegalArgumentException("There is no such 'Route' with 'id'=" + action.getId());
        }

        Route newRoute = new Route();

        newRoute.setId(action.getId());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(action.getCreateRouteDto());

        this.collection.updateElement(newRoute);

        return new ServerResponse(ResponseStatus.OK, "Route{id=" + newRoute.getId() +
                                                     ",name=" + newRoute.getName() +
                                                     "} successfully updated");
    }
}
