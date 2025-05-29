package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionAddIfMax;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;
import AstroLabServer.database.RouteDAO;

import java.sql.Connection;
import java.util.Date;
import java.util.Optional;

public class ServerAddIfMax extends ServerCommand {
    private final CustomCollection collection;
    private final RouteDAO newRouteDAO;

    public ServerAddIfMax(CustomCollection collection, RouteDAO newRouteDAO) {
        this.collection = collection;
        this.newRouteDAO = newRouteDAO;
    }

    @Override
    public ServerResponse execute(Action args) {
        ActionAddIfMax action = (ActionAddIfMax) args;

        Route newRoute = new Route();
        newRoute.setFromRouteDataTransferObject(action.getCreateRouteDto());

        try {
            Optional<Route> maxRoute = this.collection.getCollection().stream().max(Route::compareTo);
            if (maxRoute.isEmpty() || newRoute.compareTo(maxRoute.get()) > 0) {
                newRoute = newRouteDAO.create(action.getCreateRouteDto());
                collection.addElement(newRoute);
                return new ServerResponse(ResponseStatus.OK, "Route was added with id=" + newRoute.getId());
            }
            return new ServerResponse(ResponseStatus.OK, "Route was not added");
        } catch (Exception e) {
            LOGGER.error("Exception while adding if max: {}", e.getMessage());
            return new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
    }
}
