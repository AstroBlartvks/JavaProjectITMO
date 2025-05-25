package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionAdd;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;
import AstroLabServer.database.RouteDAO;

import java.sql.Connection;

public class ServerAdd extends ServerCommand {
    private final CustomCollection collection;
    private final RouteDAO newRouteDAO;

    public ServerAdd(CustomCollection collection, RouteDAO newRouteDAO) {
        this.collection = collection;
        this.newRouteDAO = newRouteDAO;
    }

    @Override
    public ServerResponse execute(Action args) {
        ActionAdd action = (ActionAdd) args;
        try {
            Route newRoute = newRouteDAO.create(action.getCreateRouteDto());
            collection.addElement(newRoute);

            return new ServerResponse(ResponseStatus.OK, "Route{id=" + newRoute.getId() +
                    ",name=" + newRoute.getName() + "} successfully added ");
        } catch (Exception e) {
            LOGGER.error("Exception while adding: {}", e.getMessage());
            return new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
    }
}
