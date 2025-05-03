package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionShow;
import org.AstroLab.actions.components.ClientServerAction;
import org.AstroLab.utils.command.CommandArgumentList;

public class ClientShow extends ClientCommand{
    @Override
    public ClientServerAction input(CommandArgumentList args) {
        return new ActionShow();
    }
}
