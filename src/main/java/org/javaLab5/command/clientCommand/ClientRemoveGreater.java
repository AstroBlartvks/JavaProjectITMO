package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.inputManager.ArgumentRequester;
import org.javaLab5.model.Coordinates;
import org.javaLab5.model.Location;

public class ClientRemoveGreater extends ClientCommand{
    @Override
    public CommandArgumentList input(){
        String name = ArgumentRequester.requestString("Write 'name' -> Route", "'name' can't be empty or null", x -> x != null && !x.isEmpty());
        Coordinates coordinates = ArgumentRequester.requestCoordinates();
        Location from = ArgumentRequester.requestLocation("from");
        Location to = ArgumentRequester.requestLocation("to");
        Double distance = ArgumentRequester.requestDouble("Write 'distance' -> Route", "Distance must be 'Double' and > 1", x -> x > 1);

        argumentList.addArgument(new CommandArgument(name));
        argumentList.addArgument(new CommandArgument(coordinates));
        argumentList.addArgument(new CommandArgument(from));
        argumentList.addArgument(new CommandArgument(to));
        argumentList.addArgument(new CommandArgument(distance));
        return argumentList;
    }
}
