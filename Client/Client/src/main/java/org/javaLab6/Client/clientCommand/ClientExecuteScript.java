package org.javaLab6.Client.clientCommand;

import org.javaLab6.Client.clientCommand.scriptHandler.ScriptExecuteScannerException;
import org.javaLab6.Client.clientCommand.scriptHandler.ScriptExecutes;
import org.javaLab6.Client.utils.command.CommandArgumentList;
import org.javaLab6.Client.inputManager.ScannerManager;


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
