package org.javaLab5.command.clientCommand;

import org.javaLab5.command.clientCommand.scriptHandler.ScriptExecuteScannerException;
import org.javaLab5.command.clientCommand.scriptHandler.ScriptExecutes;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.inputManager.ScannerManager;


public class ClientExecuteScript extends ClientCommand{
    private final ScriptExecutes scriptExecutes;

    public ClientExecuteScript(ScannerManager scannerManager){
        scriptExecutes = new ScriptExecutes(scannerManager);
    }

    @Override
    public CommandArgumentList input(CommandArgumentList argumentList) throws ScriptExecuteScannerException {
        scriptExecutes.run((String)argumentList.getFirstArgument().getValue());
        return null;
    }
}
