package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.inputManager.ArgumentRequester;
import org.javaLab5.model.Coordinates;
import org.javaLab5.model.Location;
import org.javaLab5.model.RouteDataTransferObject;

public class ClientUpdate extends ClientCommand{
    @Override
    public CommandArgumentList input() throws IllegalArgumentException {

        if (argumentList.getFirstArgument() == null){
            throw new IllegalArgumentException("The 'update' command has syntax and must contain the 'id' argument example: 'update id {element}'");
        }

        argumentList.convertArgumentType(Integer.class);

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
