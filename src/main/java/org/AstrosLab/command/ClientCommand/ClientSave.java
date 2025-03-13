package org.AstrosLab.command.ClientCommand;

import org.AstrosLab.command.CommandArgumentList;

public class ClientSave extends ClientCommand{
    @Override
    public CommandArgumentList input(String inputCommand) throws Exception {
        return CommandIdentifier.parseCommand(inputCommand);
    }
}
