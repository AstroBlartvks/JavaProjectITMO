package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionClear;
import org.AstroLab.utils.command.CommandArgumentList;

public class ClientClear extends ClientCommand{
    @Override
    public Action input(CommandArgumentList args) {
        return new ActionClear();
    }
}
