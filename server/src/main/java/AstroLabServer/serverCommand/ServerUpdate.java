package AstroLabServer.serverCommand;

import AstroLab.grpc.ClientServerActionMessage;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.CreateRouteDto;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;
import AstroLabServer.database.RouteDAO;

public class ServerUpdate extends ServerCommand {
    private final CustomCollection collection;
    private final RouteDAO newRouteDAO;

    public ServerUpdate(CustomCollection collection, RouteDAO newRouteDAO) {
        this.collection = collection;
        this.newRouteDAO = newRouteDAO;
    }

    @Override
    public ServerResponse execute(ClientServerActionMessage args) throws IllegalArgumentException {

        if (!this.collection.containsId(args.getId())) {
            throw new IllegalArgumentException("There is no such 'Route' with 'id'=" + args.getId());
        }

        if (!this.collection.getRouteInsideById(args.getId()).getOwnerLogin().equals(args.getOwnerLogin())) {
            throw new IllegalArgumentException("You can't update 'Route' with 'id'=" + args.getId() + ", because you are not owner!");
        }

        try {
            Route newRoute = newRouteDAO.create(CreateRouteDto.getFromProtobuf(args.getRouteDto()));
            collection.updateElement(newRoute);

            return new ServerResponse(ResponseStatus.OK, "Route{id=" + newRoute.getId() +
                    ",name=" + newRoute.getName() +
                    "} successfully updated");
        } catch (Exception e) {
            LOGGER.error("Exception while updating: {}", e.getMessage());
            return new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
    }
}
