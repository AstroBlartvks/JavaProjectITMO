package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionPrintFieldDescendingDistance;
import org.AstroLab.utils.command.CommandArgumentList;

public class ClientPrintFieldDescendingDistance extends ClientCommand {
    @Override
    public Action input(CommandArgumentList args) {
        return new ActionPrintFieldDescendingDistance();
    }
}
