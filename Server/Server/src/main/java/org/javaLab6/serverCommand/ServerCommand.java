package org.javaLab6.serverCommand;

import org.javaLab6.utils.ClientServer.ServerResponse;
import org.javaLab6.utils.command.CommandArgumentList;


public abstract class ServerCommand {
    public abstract ServerResponse execute(CommandArgumentList args) throws Exception;
}
