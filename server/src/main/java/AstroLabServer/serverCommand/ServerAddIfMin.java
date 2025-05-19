package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionAddIfMin;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;

import java.sql.Connection;
import java.util.Date;
import java.util.Optional;

public class ServerAddIfMin extends ServerCommand {
    private final CustomCollection collection;
    private final Connection connection;

    public ServerAddIfMin(CustomCollection collection, Connection connection) {
        this.collection = collection;
        this.connection = connection;
    }

    @Override
    public ServerResponse execute(Action args) {
        ActionAddIfMin action = (ActionAddIfMin) args;

        Route newRoute = new Route();

        newRoute.setId(collection.getNewId());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(action.getCreateRouteDto());

        Optional<Route> minRoute = this.collection.getCollection().stream().min(Route::compareTo);
        if (minRoute.isEmpty() || newRoute.compareTo(minRoute.get()) < 0) {
            this.collection.addElement(newRoute);
            return new ServerResponse(ResponseStatus.OK, "Route was added with id=" + newRoute.getId());
        }
        return new ServerResponse(ResponseStatus.OK, "Route was not added");
    }
}
