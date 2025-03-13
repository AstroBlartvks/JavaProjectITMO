package org.AstrosLab.command.ClientCommand;

import org.AstrosLab.command.CommandArgumentList;

public class ClientRemoveById extends ClientCommand {
    @Override
    public CommandArgumentList input(String inputCommand) throws Exception {
        CommandArgumentList argList = CommandIdentifier.parseCommand(inputCommand);
        argList.checkArgumentType(Integer.class);
        return argList;
    }
}
