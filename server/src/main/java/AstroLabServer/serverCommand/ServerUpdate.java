package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionUpdate;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;
import AstroLabServer.database.RouteDAO;

import java.sql.Connection;
import java.util.Date;

public class ServerUpdate extends ServerCommand {
    private final CustomCollection collection;
    private final RouteDAO newRouteDAO;

    public ServerUpdate(CustomCollection collection, RouteDAO newRouteDAO) {
        this.collection = collection;
        this.newRouteDAO = newRouteDAO;
    }

    @Override
    public ServerResponse execute(Action args) throws IllegalArgumentException {
        ActionUpdate action = (ActionUpdate) args;

        if (!this.collection.containsId(action.getId())) {
            throw new IllegalArgumentException("There is no such 'Route' with 'id'=" + action.getId());
        }

        if (!this.collection.getRouteInsideById(action.getId()).getOwnerLogin().equals(action.getOwnerLogin())) {
            throw new IllegalArgumentException("You can't update 'Route' with 'id'=" + action.getId() + ", because you are not owner!");
        }

        try {
            Route newRoute = newRouteDAO.create(action.getCreateRouteDto());
            collection.updateElement(newRoute);

            return new ServerResponse(ResponseStatus.OK, "Route{id=" + newRoute.getId() +
                    ",name=" + newRoute.getName() +
                    "} successfully updated");
        } catch (Exception e) {
            LOGGER.error("Exception while updating: {}", e.getMessage());
            return new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
    }
}
