package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionHelp;
import org.AstroLab.utils.command.CommandArgumentList;

public class ClientHelp extends ClientCommand{
    @Override
    public Action input(CommandArgumentList args) {
        return new ActionHelp();
    }
}
