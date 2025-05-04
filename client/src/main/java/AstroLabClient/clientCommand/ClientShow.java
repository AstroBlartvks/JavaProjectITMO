package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionShow;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.utils.command.CommandArgumentList;

public class ClientShow extends ClientCommand {
    @Override
    public ClientServerAction input(CommandArgumentList args) {
        return new ActionShow();
    }
}
