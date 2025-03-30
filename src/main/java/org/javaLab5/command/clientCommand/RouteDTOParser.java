package org.javaLab5.command.clientCommand;


import org.javaLab5.inputManager.ArgumentRequester;
import org.javaLab5.model.Coordinates;
import org.javaLab5.model.Location;
import org.javaLab5.model.CreateRouteDTO;

import java.util.Optional;

public class RouteDTOParser {
    public static CreateRouteDTO parse(ArgumentRequester argumentRequester){
        Optional<String> name = argumentRequester.requestString("Write 'name' -> Route", "'name' can't be empty or null", x -> x != null && !x.isEmpty());
        Coordinates coordinates = argumentRequester.requestCoordinates();
        Location from = argumentRequester.requestLocation("from");
        Location to = argumentRequester.requestLocation("to");
        Optional<Double> distance = argumentRequester.requestDouble("Write 'distance' -> Route", "Distance must be 'Double' and > 1", x -> x > 1);

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Invalid input for 'name'. 'Name' cannot be null.");
        }

        if (distance.isEmpty()) {
            throw new IllegalArgumentException("Invalid input for 'distance'. 'distance' cannot be null.");
        }

        CreateRouteDTO routeDTO = new CreateRouteDTO();
        routeDTO.setName(name.get());
        routeDTO.setCoordinates(coordinates);
        routeDTO.setFrom(from);
        routeDTO.setTo(to);
        routeDTO.setDistance(distance.get());
        return routeDTO;
    }
}
