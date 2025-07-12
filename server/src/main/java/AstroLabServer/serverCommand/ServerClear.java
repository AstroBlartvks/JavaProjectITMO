package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.grpc.ClientServerActionMessage;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;
import AstroLabServer.database.RouteDAO;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerClear extends ServerCommand {
    private final CustomCollection collection;
    private final RouteDAO newRouteDAO;

    public ServerClear(CustomCollection collection, RouteDAO newRouteDAO) {
        this.collection = collection;
        this.newRouteDAO = newRouteDAO;
    }

    @Override
    public ServerResponse execute(ClientServerActionMessage args) {
        StringBuilder response = new StringBuilder();

        Set<Integer> greaterIds = this.collection.getCollection().stream()
                .map(Route::getId)
                .collect(Collectors.toSet());

        for (int index : greaterIds) {
            try {
                if (!this.collection.getRouteInsideById(index).getOwnerLogin().equals(args.getOwnerLogin())) {
                    response.append("You can't update 'Route' with 'id'=").append(index).append(", because you are not owner!");
                    continue;
                }
                newRouteDAO.remove(this.collection.getRouteInsideById(index));
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
}
