package org.javaLab5.command.clientCommand;

import org.javaLab5.command.clientCommand.scriptHandler.ScriptExecutes;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.inputManager.NewScannerManager;


public class ClientExecuteScript extends ClientCommand{
    private final ScriptExecutes scriptExecutes;

    public ClientExecuteScript(NewScannerManager newScannerManager){
        scriptExecutes = new ScriptExecutes(newScannerManager);
    }

    @Override
    public CommandArgumentList input() throws Exception{
        scriptExecutes.run((String)argumentList.getFirstArgument().getValue());
        return null;
    }
}
