package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;

public class ClientUpdate extends ClientCommand{
    @Override
    public CommandArgumentList input() throws IllegalArgumentException {

        if (argumentList.getFirstArgument() == null){
            throw new IllegalArgumentException("The 'update' command has syntax and must contain the 'id' argument example: 'update id {element}'");
        }

        argumentList.convertArgumentType(Integer.class);
        argumentList.addArgument(new CommandArgument(RouteDTOParser.parse()));

        return argumentList;
    }
}
