package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionAdd;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLabClient.inputManager.ArgumentRequester;
import org.AstroLabClient.inputManager.SystemInClosedException;


public class ClientAdd extends ClientCommand{
    private final ArgumentRequester argumentRequester;

    public ClientAdd(ArgumentRequester argumentRequester){
        this.argumentRequester = argumentRequester;
    }
    /**
     * Command 'Add'
     * @return CommandArgumentList arguments of command
     */
    @Override
    public Action input(CommandArgumentList argumentList) throws IllegalArgumentException, SystemInClosedException {
        ActionAdd action = new ActionAdd();
        action.setCreateRouteDTO(RouteDTOParser.parse(this.argumentRequester));
        return action;
    }
}
