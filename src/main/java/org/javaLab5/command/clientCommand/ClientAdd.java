package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.inputManager.ArgumentRequester;
import org.javaLab5.model.Coordinates;
import org.javaLab5.model.Location;
import org.javaLab5.model.RouteDataTransferObject;


public class ClientAdd extends ClientCommand{
    /**
     * Command 'Add'
     * @return CommandArgumentList arguments of command
     */
    @Override
    public CommandArgumentList input() {
        String name = ArgumentRequester.requestString("Write 'name' -> Route", "'name' can't be empty or null", x -> x != null && !x.isEmpty());
        Coordinates coordinates = ArgumentRequester.requestCoordinates();
        Location from = ArgumentRequester.requestLocation("from");
        Location to = ArgumentRequester.requestLocation("to");
        Double distance = ArgumentRequester.requestDouble("Write 'distance' -> Route", "Distance must be 'Double' and > 1", x -> x > 1);

        RouteDataTransferObject routeDTO = new RouteDataTransferObject();
        routeDTO.setName(name);
        routeDTO.setCoordinates(coordinates);
        routeDTO.setFrom(from);
        routeDTO.setTo(to);
        routeDTO.setDistance(distance);
        argumentList.addArgument(new CommandArgument(routeDTO));

        return argumentList;
    }
}
