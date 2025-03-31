package org.javaLab5.command.serverCommand;

import org.javaLab5.command.CommandArgumentList;


public abstract class ServerCommand {
    public abstract ServerResponse execute(CommandArgumentList args) throws Exception;
}
