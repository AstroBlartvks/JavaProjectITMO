package org.AstrosLab.command.ClientCommand;

import org.AstrosLab.command.ClientCommand.ScriptHandler.ScriptExecuter;
import org.AstrosLab.command.CommandArgumentList;

//
public class ClientExecuteScript extends ClientCommand{
    @Override
    public CommandArgumentList input(String inputCommand) throws Exception {
        CommandArgumentList args = CommandIdentifier.parseCommand(inputCommand);
        ScriptExecuter scriptExecuter = new ScriptExecuter();
        scriptExecuter.execute((String)args.getFirstArgument().getValue());
        return null;
    }
}
