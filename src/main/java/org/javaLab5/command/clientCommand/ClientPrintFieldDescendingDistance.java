package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgumentList;

public class ClientPrintFieldDescendingDistance extends ClientCommand {
    @Override
    public CommandArgumentList input(String inputCommand) throws Exception {
        return CommandIdentifier.parseCommand(inputCommand);
    }
}
