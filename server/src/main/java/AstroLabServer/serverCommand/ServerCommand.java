package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.utils.ClientServer.ServerResponse;


public abstract class ServerCommand {
    public abstract ServerResponse execute(Action action) throws Exception;
}
