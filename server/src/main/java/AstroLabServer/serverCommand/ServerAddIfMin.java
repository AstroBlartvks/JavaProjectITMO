package AstroLabServer.serverCommand;

import AstroLab.grpc.ClientServerActionMessage;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLab.utils.model.CreateRouteDto;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;
import AstroLabServer.database.RouteDAO;

import java.util.Optional;

public class ServerAddIfMin extends ServerCommand {
    private final CustomCollection collection;
    private final RouteDAO newRouteDAO;

    public ServerAddIfMin(CustomCollection collection, RouteDAO newRouteDAO) {
        this.collection = collection;
        this.newRouteDAO = newRouteDAO;
    }

    @Override
    public ServerResponse execute(ClientServerActionMessage args) {
        CreateRouteDto createRouteDto = CreateRouteDto.getFromProtobuf(args.getRouteDto());

        Route newRoute = new Route();
        newRoute.setFromRouteDataTransferObject(createRouteDto);

        try{
            Optional<Route> minRoute = this.collection.getCollection().stream().min(Route::compareTo);
            if (minRoute.isEmpty() || newRoute.compareTo(minRoute.get()) < 0) {
                newRoute = newRouteDAO.create(createRouteDto);
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
