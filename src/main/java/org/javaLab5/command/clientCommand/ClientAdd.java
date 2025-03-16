package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.inputManager.ArgumentRequester;
import org.javaLab5.model.Coordinates;
import org.javaLab5.model.Location;


public class ClientAdd extends ClientCommand{
    /**
     * Command 'Add'
     * @param inputCommand command String
     * @return CommandArgumentList arguments of command
     * @throws Exception while creating arguments
     */
    @Override
    public CommandArgumentList input(String inputCommand) throws Exception {
        CommandArgumentList args = CommandIdentifier.parseCommand(inputCommand);

        String name = ArgumentRequester.requestString("Write 'name' -> Route", "'name' can't be empty or null", x -> x != null && !x.isEmpty());
        Coordinates coordinates = ArgumentRequester.requestCoordinates();
        Location from = ArgumentRequester.requestLocation("from");
        Location to = ArgumentRequester.requestLocation("to");
        Double distance = ArgumentRequester.requestDouble("Write 'distance' -> Route", "Distance must be 'Double' and > 1", x -> x > 1);

        args.addArgument(new CommandArgument(name));
        args.addArgument(new CommandArgument(coordinates));
        args.addArgument(new CommandArgument(from));
        args.addArgument(new CommandArgument(to));
        args.addArgument(new CommandArgument(distance));
        return args;
    }
}
