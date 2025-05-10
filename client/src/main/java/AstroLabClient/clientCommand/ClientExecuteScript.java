package AstroLabClient.clientCommand;

import AstroLab.actions.components.ClientServerAction;
import AstroLab.utils.command.CommandArgumentList;
import AstroLabClient.clientCommand.scriptHandler.ScriptExecuteScannerException;
import AstroLabClient.clientCommand.scriptHandler.ScriptExecutes;
import AstroLabClient.inputManager.ScannerManager;

public class ClientExecuteScript extends ClientCommand {
    private final ScriptExecutes scriptExecutes;

    public ClientExecuteScript(ScannerManager scannerManager) {
        scriptExecutes = new ScriptExecutes(scannerManager);
    }

    @Override
    public ClientServerAction input(CommandArgumentList argumentList) throws ScriptExecuteScannerException {
        scriptExecutes.run((String) argumentList.getFirstArgument().getValue());
        return null;
    }
}
