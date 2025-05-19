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
    private final Connection connection;

    public ServerAdd(CustomCollection collection, Connection connection) {
        this.collection = collection;
        this.connection = connection;
    }

    @Override
    public ServerResponse execute(Action args) {
        ActionAdd action = (ActionAdd) args;
        RouteDAO newRouteDAO = new RouteDAO(this.connection);
        try {
            Route newRoute = newRouteDAO.create(action.getCreateRouteDto(), action.getOwnerLogin());
            collection.addElement(newRoute);

            return new ServerResponse(ResponseStatus.OK, "Route{id=" + newRoute.getId() +
                    ",name=" + newRoute.getName() + "} successfully added ");
        } catch (Exception e) {
            return new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
    }
}
