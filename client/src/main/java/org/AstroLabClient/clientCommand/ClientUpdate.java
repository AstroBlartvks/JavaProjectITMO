package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionUpdate;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLabClient.inputManager.ArgumentRequester;
import org.AstroLabClient.inputManager.SystemInClosedException;

public class ClientUpdate extends ClientCommand{
    private final ArgumentRequester argumentRequester;

    public ClientUpdate(ArgumentRequester argumentRequester){
        this.argumentRequester = argumentRequester;
    }

    @Override
    public Action input(CommandArgumentList argumentList) throws IllegalArgumentException, SystemInClosedException {

        if (argumentList.getFirstArgument() == null){
            throw new IllegalArgumentException("The 'update' command has syntax and must contain the 'id' argument example: 'update id {element}'");
        }

        ActionUpdate action = new ActionUpdate();
        action.setId(argumentList.convertArgumentToNeedType(Integer::valueOf));
        action.setCreateRouteDTO(RouteDTOParser.parse(this.argumentRequester));
        return action;
    }
}
