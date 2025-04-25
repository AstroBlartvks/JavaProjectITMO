package org.AstroLab.clientCommand;

import org.AstroLab.clientCommand.scriptHandler.ScriptExecuteScannerException;
import org.AstroLab.clientCommand.scriptHandler.ScriptExecutes;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLab.inputManager.ScannerManager;


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
