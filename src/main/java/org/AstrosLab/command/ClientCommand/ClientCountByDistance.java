package org.AstrosLab.command.ClientCommand;

import org.AstrosLab.command.CommandArgumentList;

public class ClientCountByDistance extends ClientCommand{
    @Override
    public CommandArgumentList input(String inputCommand) throws Exception{
        CommandArgumentList argList = CommandIdentifier.parseCommand(inputCommand);
        argList.checkArgumentType(Double.class);
        return argList;
    }
}
