package org.AstrosLab.command.ClientCommand;

import org.AstrosLab.command.CommandArgumentList;


public abstract class ClientCommand {
    public abstract CommandArgumentList input(String inputCommand) throws Exception;
}
