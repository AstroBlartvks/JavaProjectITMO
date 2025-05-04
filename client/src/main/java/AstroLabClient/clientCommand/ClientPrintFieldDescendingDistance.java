package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionPrintFieldDescendingDistance;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.utils.command.CommandArgumentList;

public class ClientPrintFieldDescendingDistance extends ClientCommand {
    @Override
    public ClientServerAction input(CommandArgumentList args) {
        return new ActionPrintFieldDescendingDistance();
    }
}
