package AstroLabClient.clientCommand;

import AstroLab.utils.command.CommandArgumentList;
import AstroLabClient.clientAction.ActionExit;
import AstroLabClient.clientAction.ClientAction;

public class ClientExit extends ClientCommand {
    @Override
    public ClientAction input(CommandArgumentList args) {
        return new ActionExit();
    }
}
