package org.AstrosLab.command.ServerCommand;

import org.AstrosLab.command.CommandArgumentList;


public abstract class ServerCommand {
    public abstract String execute(CommandArgumentList args) throws Exception;
}
