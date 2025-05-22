package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionRemoveGreater;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;
import AstroLabServer.database.RouteDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerRemoveGreater extends ServerCommand {
    private final CustomCollection collection;
    private final Connection connection;

    public ServerRemoveGreater(CustomCollection collection, Connection connection) {
        this.collection = collection;
        this.connection = connection;
    }

    @Override
    public ServerResponse execute(Action args) {
        Route newRoute = handleNewRoute(args);
        RouteDAO routeDAO = new RouteDAO(this.connection);

        Set<Integer> greaterIds = this.collection.getCollection().stream()
                .filter(route -> route.compareTo(newRoute) > 0)
                .map(Route::getId)
                .collect(Collectors.toSet());

        StringBuilder response = new StringBuilder();

        for (int index : greaterIds) {
            try {
                if (!this.collection.getRouteInsideById(index).getOwnerLogin().equals(newRoute.getOwnerLogin())) {
                    throw new IllegalArgumentException("You can't update 'Route' with 'id'=" + index + ", because you are not owner!");
                }
                routeDAO.remove(this.collection.getRouteInsideById(index));
                collection.removeById(index);
                response.append("Route with id=")
                        .append(index)
                        .append(" was deleted!\n");
            } catch (SQLException | IllegalArgumentException e) {
                LOGGER.error("Error while removing greater with id = {}: {}", index, e.getMessage());
                response.append("Route with id=")
                        .append(index)
                        .append(" was NOT deleted!, because: ")
                        .append(e.getMessage())
                        .append("\n");
            }
        }

        return new ServerResponse(ResponseStatus.OK, response.isEmpty() ? "Nothing removed" : response);
    }

    private Route handleNewRoute(Action args) {
        ActionRemoveGreater action = (ActionRemoveGreater) args;
        Route newRoute = new Route();

        newRoute.setId(collection.getNewId());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(action.getCreateRouteDto());
        newRoute.setOwnerLogin(action.getOwnerLogin());

        return newRoute;
    }
}
