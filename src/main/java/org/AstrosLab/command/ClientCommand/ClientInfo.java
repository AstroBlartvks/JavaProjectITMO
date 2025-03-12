package org.AstrosLab.command.ClientCommand;

import org.AstrosLab.command.CommandArgumentList;

public class ClientInfo extends ClientCommand{
    @Override
    public CommandArgumentList input() {
        CommandArgumentList args = new CommandArgumentList("info");
        return args;
    }
}
