package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionRemoveGreater;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLabClient.inputManager.ArgumentRequester;
import org.AstroLabClient.inputManager.SystemInClosedException;

public class ClientRemoveGreater extends ClientCommand{
    private final ArgumentRequester argumentRequester;

    public ClientRemoveGreater(ArgumentRequester argumentRequester){
        this.argumentRequester = argumentRequester;
    }
    @Override
    public Action input(CommandArgumentList argumentList) throws IllegalArgumentException, SystemInClosedException {
        ActionRemoveGreater action = new ActionRemoveGreater();
        action.setCreateRouteDTO(RouteDTOParser.parse(this.argumentRequester));
        return action;
    }
}
