package org.AstrosLab.command.ClientCommand;

import org.AstrosLab.command.CommandArgument;
import org.AstrosLab.command.CommandArgumentList;
import org.AstrosLab.inputManager.ArgumentRequester;
import org.AstrosLab.model.Coordinates;
import org.AstrosLab.model.Location;

public class ClientUpdate extends ClientCommand{
    @Override
    public CommandArgumentList input(String inputCommand) throws Exception {
        CommandArgumentList args = CommandIdentifier.parseCommand(inputCommand);

        if (args == null){
            throw new Exception("Command can't be null: '"+inputCommand+"'");
        }

        if (args.getFirstArgument() == null || args.getFirstArgument().getValue() == null){
            throw new Exception("The 'update' command has syntax and must contain the 'id' argument example: 'update id {element}'");
        }

        args.checkArgumentType(Integer.class);

        String name = ArgumentRequester.requestString("Write 'name' -> Route", "'name' can't be empty or null", x -> x != null && !x.isEmpty());
        Coordinates coordinates = ArgumentRequester.requestCoordinates();
        Location from = ArgumentRequester.requestLocation("from");
        Location to = ArgumentRequester.requestLocation("to");
        Double distance = ArgumentRequester.requestDouble("Write 'distance' -> Route", "Diastance must be 'Double' and > 1", x -> x > 1);

        args.addArgument(new CommandArgument(name));
        args.addArgument(new CommandArgument(coordinates));
        args.addArgument(new CommandArgument(from));
        args.addArgument(new CommandArgument(to));
        args.addArgument(new CommandArgument(distance));
        return args;
    }
}
