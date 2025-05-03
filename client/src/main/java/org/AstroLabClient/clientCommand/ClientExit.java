package org.AstroLabClient.clientCommand;

import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLabClient.clientAction.ActionExit;
import org.AstroLabClient.clientAction.ClientAction;

public class ClientExit extends ClientCommand{
    @Override
    public ClientAction input(CommandArgumentList args) {
        return new ActionExit();
    }
}
