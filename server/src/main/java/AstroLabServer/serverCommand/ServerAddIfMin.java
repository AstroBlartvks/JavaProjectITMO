package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionAddIfMin;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;
import AstroLabServer.database.RouteDAO;

import java.sql.Connection;
import java.util.Date;
import java.util.Optional;

public class ServerAddIfMin extends ServerCommand {
    private final CustomCollection collection;
    private final RouteDAO newRouteDAO;

    public ServerAddIfMin(CustomCollection collection, RouteDAO newRouteDAO) {
        this.collection = collection;
        this.newRouteDAO = newRouteDAO;
    }

    @Override
    public ServerResponse execute(Action args) {
        ActionAddIfMin action = (ActionAddIfMin) args;

        Route newRoute = new Route();
        newRoute.setFromRouteDataTransferObject(action.getCreateRouteDto());

        try{
            Optional<Route> minRoute = this.collection.getCollection().stream().min(Route::compareTo);
            if (minRoute.isEmpty() || newRoute.compareTo(minRoute.get()) < 0) {
                newRoute = newRouteDAO.create(action.getCreateRouteDto());
                collection.addElement(newRoute);
                this.collection.addElement(newRoute);
                return new ServerResponse(ResponseStatus.OK, "Route was added with id=" + newRoute.getId());
            }
            return new ServerResponse(ResponseStatus.OK, "Route was not added");
        } catch (Exception e) {
            LOGGER.error("Exception while adding if min: {}", e.getMessage());
            return new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
    }
}
