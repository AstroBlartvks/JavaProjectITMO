package org.AstroLab.serverCommand;

import org.AstroLab.collection.CustomCollection;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;

public class ServerPrintFieldDescendingDistance extends ServerCommand {
    private final CustomCollection collection;

    public ServerPrintFieldDescendingDistance(CustomCollection collection) {
        this.collection = collection;
    }
    @Override
    public ServerResponse execute(CommandArgumentList args) {
        return new ServerResponse(ResponseStatus.OK, this.collection.printFieldDescendingDistance());
    }
}
