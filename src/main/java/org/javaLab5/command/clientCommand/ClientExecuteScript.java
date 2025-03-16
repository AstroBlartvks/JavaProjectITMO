package org.javaLab5.command.clientCommand;

import org.javaLab5.command.clientCommand.scriptHandler.ScriptExecutes;
import org.javaLab5.command.CommandArgumentList;


public class ClientExecuteScript extends ClientCommand{
    @Override
    public CommandArgumentList input(String inputCommand) throws Exception {
        CommandArgumentList args = CommandIdentifier.parseCommand(inputCommand);
        ScriptExecutes scriptExecutes = new ScriptExecutes();
        scriptExecutes.execute((String)args.getFirstArgument().getValue());
        return null;
    }
}
