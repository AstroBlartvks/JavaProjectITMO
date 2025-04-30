package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionAddIfMax;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLabClient.inputManager.ArgumentRequester;
import org.AstroLabClient.inputManager.SystemInClosedException;

public class ClientAddIfMax extends ClientCommand{
    private final ArgumentRequester argumentRequester;

    public ClientAddIfMax(ArgumentRequester argumentRequester){
        this.argumentRequester = argumentRequester;
    }
    /**
     * Command 'Add'
     * @return CommandArgumentList arguments of command
     */
    @Override
    public Action input(CommandArgumentList argumentList) throws IllegalArgumentException, SystemInClosedException {
        ActionAddIfMax action = new ActionAddIfMax();
        action.setCreateRouteDTO(RouteDTOParser.parse(this.argumentRequester));
        return action;
    }
}
