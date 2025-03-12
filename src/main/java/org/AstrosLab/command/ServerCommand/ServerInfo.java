package org.AstrosLab.command.ServerCommand;

import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.command.CommandArgumentList;

public class ServerInfo extends ServerCommand{
    private CustomCollection collection;

    public ServerInfo(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public String execute(CommandArgumentList args) throws Exception {
        return this.collection.toString();
    }
}
