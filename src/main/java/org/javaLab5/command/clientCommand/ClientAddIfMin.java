package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;

public class ClientAddIfMin extends ClientCommand {
    @Override
    public CommandArgumentList input() {
        argumentList.addArgument(new CommandArgument(RouteDTOParser.parse()));
        return argumentList;
    }
}
