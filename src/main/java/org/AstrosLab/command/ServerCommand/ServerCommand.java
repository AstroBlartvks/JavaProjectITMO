package org.AstrosLab.command.ServerCommand;

import org.AstrosLab.command.CommandArgumentList;


public abstract class ServerCommand {
    public abstract ServerResponse execute(CommandArgumentList args) throws Exception;
}
