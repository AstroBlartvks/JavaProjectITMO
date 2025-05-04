package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionClear;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.utils.command.CommandArgumentList;

public class ClientClear extends ClientCommand {
    @Override
    public ClientServerAction input(CommandArgumentList args) {
        return new ActionClear();
    }
}
