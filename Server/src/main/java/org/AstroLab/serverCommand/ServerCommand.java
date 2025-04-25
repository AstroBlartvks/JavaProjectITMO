package org.AstroLab.serverCommand;

import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;


public abstract class ServerCommand {
    public abstract ServerResponse execute(CommandArgumentList args) throws Exception;
}
