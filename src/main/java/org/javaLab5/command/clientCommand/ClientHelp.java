package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgumentList;

public class ClientHelp extends ClientCommand{
    @Override
    public CommandArgumentList input(String inputCommand) throws Exception {
        return CommandIdentifier.parseCommand(inputCommand);
    }
}
