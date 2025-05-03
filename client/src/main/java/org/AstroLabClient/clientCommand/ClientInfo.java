package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionInfo;
import org.AstroLab.actions.components.ClientServerAction;
import org.AstroLab.utils.command.CommandArgumentList;

public class ClientInfo extends ClientCommand{
    @Override
    public ClientServerAction input(CommandArgumentList args) {
        return new ActionInfo();
    }
}
