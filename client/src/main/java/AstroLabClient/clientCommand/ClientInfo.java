package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionInfo;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.utils.command.CommandArgumentList;

public class ClientInfo extends ClientCommand {
    @Override
    public ClientServerAction input(CommandArgumentList args) {
        return new ActionInfo();
    }
}
