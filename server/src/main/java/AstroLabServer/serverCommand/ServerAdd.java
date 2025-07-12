package AstroLabServer.serverCommand;

import AstroLab.grpc.ClientServerActionMessage;
import AstroLab.grpc.RouteDto;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.CreateRouteDto;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;
import AstroLabServer.database.RouteDAO;

public class ServerAdd extends ServerCommand {
    private final CustomCollection collection;
    private final RouteDAO newRouteDAO;

    public ServerAdd(CustomCollection collection, RouteDAO newRouteDAO) {
        this.collection = collection;
        this.newRouteDAO = newRouteDAO;
    }

    @Override
    public ServerResponse execute(ClientServerActionMessage action) {
        try {
            RouteDto routeDto = action.getRouteDto();
            Route newRoute = newRouteDAO.create(CreateRouteDto.getFromProtobuf(routeDto));
            collection.addElement(newRoute);

            return new ServerResponse(ResponseStatus.OK, "Route{id=" + newRoute.getId() +
                    ",name=" + newRoute.getName() + "} successfully added ");
        } catch (Exception e) {
            LOGGER.error("Exception while adding: {}", e.getMessage());
            return new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
    }
}
