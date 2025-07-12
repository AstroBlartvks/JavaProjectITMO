package AstroLabServer.serverCommand;

import AstroLab.actions.components.Action;
import AstroLab.grpc.ClientServerActionMessage;
import AstroLab.utils.ClientServer.ServerResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ServerCommand {
    protected final Logger LOGGER = LogManager.getLogger(ServerCommand.class);

    public abstract ServerResponse execute(ClientServerActionMessage action) throws Exception;
}
