package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLabClient.clientCommand.scriptHandler.ScriptExecuteScannerException;
import org.AstroLabClient.clientCommand.scriptHandler.ScriptExecutes;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLabClient.inputManager.ScannerManager;


public class ClientExecuteScript extends ClientCommand{
    private final ScriptExecutes scriptExecutes;

    public ClientExecuteScript(ScannerManager scannerManager){
        scriptExecutes = new ScriptExecutes(scannerManager);
    }

    @Override
    public Action input(CommandArgumentList argumentList) throws ScriptExecuteScannerException {
        scriptExecutes.run((String)argumentList.getFirstArgument().getValue());
        return null;
    }
}
