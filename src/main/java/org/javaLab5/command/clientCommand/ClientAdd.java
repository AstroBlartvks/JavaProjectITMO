package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;


public class ClientAdd extends ClientCommand{
    /**
     * Command 'Add'
     * @return CommandArgumentList arguments of command
     */
    @Override
    public CommandArgumentList input() {
        argumentList.addArgument(new CommandArgument(RouteDTOParser.parse()));
        return argumentList;
    }
}
