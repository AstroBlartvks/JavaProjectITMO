package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionAddIfMin;
import org.AstroLab.actions.components.ClientServerAction;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLabClient.inputManager.ArgumentRequester;
import org.AstroLabClient.inputManager.SystemInClosedException;

public class ClientAddIfMin extends ClientCommand {
    private final ArgumentRequester argumentRequester;

    public ClientAddIfMin(ArgumentRequester argumentRequester){
        this.argumentRequester = argumentRequester;
    }
    /**
     * Command 'Add'
     * @return CommandArgumentList arguments of command
     */
    @Override
    public ClientServerAction input(CommandArgumentList argumentList) throws IllegalArgumentException, SystemInClosedException {
        ActionAddIfMin action = new ActionAddIfMin();
        action.setCreateRouteDTO(RouteDTOParser.parse(this.argumentRequester));
        return action;

    }
}
