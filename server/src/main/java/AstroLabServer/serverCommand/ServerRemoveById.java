package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionRemoveById;
import AstroLab.grpc.ClientServerActionMessage;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLabServer.collection.CustomCollection;
import AstroLabServer.database.RouteDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class ServerRemoveById extends ServerCommand {
    private final CustomCollection collection;
    private final RouteDAO newRouteDAO;

    public ServerRemoveById(CustomCollection collection, RouteDAO newRouteDAO) {
        this.collection = collection;
        this.newRouteDAO = newRouteDAO;
    }

    @Override
    public ServerResponse execute(ClientServerActionMessage args) throws Exception {
        int id = args.getId();

        if (!this.collection.containsId(id)) {
            throw new Exception("There is no 'id'=" + id + " in the collection");
        }

        if (!this.collection.getRouteInsideById(args.getId()).getOwnerLogin().equals(args.getOwnerLogin())) {
            throw new IllegalArgumentException("You can't update 'Route' with 'id'=" + args.getId() + ", because you are not owner!");
        }

        try {
            newRouteDAO.remove(this.collection.getRouteInsideById(id));
            this.collection.removeById(id);
            LOGGER.info("Route id = {} deleted successfully!", id);
            return new ServerResponse(ResponseStatus.OK, "Route deleted successfully");
        } catch (SQLException e) {
            LOGGER.error("Route can't be deleted: {}", e.getMessage());
            return new ServerResponse(ResponseStatus.EXCEPTION, "Route can't be deleted: " + e.getMessage());
        }
    }
}
