package org.AstrosLab.command.ServerCommand;

import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.command.CommandArgumentList;

public class ServerPrintFieldDescendingDistance extends ServerCommand {
    private final CustomCollection collection;

    public ServerPrintFieldDescendingDistance(CustomCollection collection) {
        this.collection = collection;
    }
    @Override
    public ServerResponse execute(CommandArgumentList args) throws Exception {
        return new ServerResponse(ResponseStatus.OK, this.collection.printFieldDescendingDistance());
    }
}
