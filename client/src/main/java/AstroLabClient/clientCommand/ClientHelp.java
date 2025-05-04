package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionHelp;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.utils.command.CommandArgumentList;

public class ClientHelp extends ClientCommand {
    @Override
    public ClientServerAction input(CommandArgumentList args) {
        return new ActionHelp();
    }
}
