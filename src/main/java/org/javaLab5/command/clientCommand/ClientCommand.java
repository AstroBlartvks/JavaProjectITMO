package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgumentList;


public abstract class ClientCommand {
    public abstract CommandArgumentList input(String inputCommand) throws Exception;
}
