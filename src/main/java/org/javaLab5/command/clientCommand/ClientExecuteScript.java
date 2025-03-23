package org.javaLab5.command.clientCommand;

import org.javaLab5.command.clientCommand.scriptHandler.ScriptExecutes;
import org.javaLab5.command.CommandArgumentList;


public class ClientExecuteScript extends ClientCommand{
    @Override
    public CommandArgumentList input() throws Exception{
        ScriptExecutes scriptExecutes = new ScriptExecutes();
        scriptExecutes.execute((String)argumentList.getFirstArgument().getValue());
        return null;
    }
}
