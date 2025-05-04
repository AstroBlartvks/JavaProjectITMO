package AstroLabClient.clientCommand;

import AstroLab.utils.model.Coordinates;
import AstroLab.utils.model.CreateRouteDto;
import AstroLab.utils.model.Location;
import AstroLabClient.inputManager.ArgumentRequester;
import AstroLabClient.inputManager.SystemInClosedException;
import java.util.Optional;

public class RouteDtoParser {
    public static CreateRouteDto parse(ArgumentRequester argumentRequester)
            throws IllegalArgumentException, SystemInClosedException {
        Optional<String> name = argumentRequester.requestString("Write 'name' -> Route",
                "'name' can't be empty or null",
                x -> x != null && !x.isEmpty());
        Optional<Double> distance = argumentRequester.requestDouble("Write 'distance' -> Route",
                "Distance must be 'Double' and > 1",
                x -> x > 1);

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Invalid input for 'name'. 'Name' cannot be null.");
        }

        if (distance.isEmpty()) {
            throw new IllegalArgumentException("Invalid input for 'distance'. 'distance' cannot be null.");
        }

        Coordinates coordinates = argumentRequester.requestCoordinates();
        Location from = argumentRequester.requestLocation("from");
        Location to = argumentRequester.requestLocation("to");

        CreateRouteDto routeDto = new CreateRouteDto();
        routeDto.setName(name.get());
        routeDto.setCoordinates(coordinates);
        routeDto.setFrom(from);
        routeDto.setTo(to);
        routeDto.setDistance(distance.get());
        return routeDto;
    }
}
