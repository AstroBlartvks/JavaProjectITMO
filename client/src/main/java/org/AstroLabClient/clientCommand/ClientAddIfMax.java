package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.ActionAddIfMax;
import org.AstroLab.actions.components.ClientServerAction;
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
    public ClientServerAction input(CommandArgumentList argumentList) throws IllegalArgumentException, SystemInClosedException {
        ActionAddIfMax action = new ActionAddIfMax();
        action.setCreateRouteDTO(RouteDTOParser.parse(this.argumentRequester));
        return action;
    }
}
