package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionRemoveById;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLabServer.collection.CustomCollection;
import AstroLabServer.database.RouteDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class ServerRemoveById extends ServerCommand {
    private final CustomCollection collection;
    private final Connection connection;

    public ServerRemoveById(CustomCollection collection, Connection connection) {
        this.collection = collection;
        this.connection = connection;
    }

    @Override
    public ServerResponse execute(Action args) throws Exception {
        ActionRemoveById action = (ActionRemoveById) args;
        RouteDAO routeDAO = new RouteDAO(this.connection);

        int id = action.getId();

        if (!this.collection.containsId(id)) {
            throw new Exception("There is no 'id'=" + id + " in the collection");
        }

        if (!this.collection.getRouteInsideById(action.getId()).getOwnerLogin().equals(action.getOwnerLogin())) {
            throw new IllegalArgumentException("You can't update 'Route' with 'id'=" + action.getId() + ", because you are not owner!");
        }

        try {

            routeDAO.remove(this.collection.getRouteInsideById(id));
            this.collection.removeById(id);
            LOGGER.info("Route id = {} deleted successfully!", id);
            return new ServerResponse(ResponseStatus.OK, "Route deleted successfully");
        } catch (SQLException e) {
            LOGGER.error("Route can't be deleted: {}", e.getMessage());
            return new ServerResponse(ResponseStatus.EXCEPTION, "Route can't be deleted: " + e.getMessage());
        }
    }
}
