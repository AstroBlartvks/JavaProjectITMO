package org.AstroLabServer.serverCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;


public abstract class ServerCommand {
    public abstract ServerResponse execute(Action action) throws Exception;
}
