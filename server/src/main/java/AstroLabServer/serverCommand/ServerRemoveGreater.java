package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionRemoveGreater;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;

import java.sql.Connection;
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

        Set<Integer> greaterIds = this.collection.getCollection().stream()
                .filter(route -> route.compareTo(newRoute) > 0)
                .map(Route::getId)
                .collect(Collectors.toSet());

        StringBuilder response = new StringBuilder();

        for (int index : greaterIds) {
            collection.removeById(index);
            response.append("Route with id=").append(index).append(" was deleted!\n");
        }

        return new ServerResponse(ResponseStatus.OK, response.isEmpty() ? "Nothing removed" : response);
    }

    private Route handleNewRoute(Action args) {
        ActionRemoveGreater action = (ActionRemoveGreater) args;
        Route newRoute = new Route();

        newRoute.setId(collection.getNewId());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(action.getCreateRouteDto());

        return newRoute;
    }
}
