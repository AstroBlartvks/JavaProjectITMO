package org.AstrosLab.command.ClientCommand;

import org.AstrosLab.command.CommandArgumentList;

public class ClientCountByDistance extends ClientCommand{
    @Override
    public CommandArgumentList input(String inputCommand) {
        return CommandIdentifier.parseCommand(inputCommand);
    }
}
