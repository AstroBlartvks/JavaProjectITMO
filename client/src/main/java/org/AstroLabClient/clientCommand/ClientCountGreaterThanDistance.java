package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionCountGreaterThanDistance;
import org.AstroLab.utils.command.CommandArgumentList;

public class ClientCountGreaterThanDistance extends ClientCommand{
    @Override
    public Action input(CommandArgumentList argumentList){
        ActionCountGreaterThanDistance action = new ActionCountGreaterThanDistance();
        action.setDistance(argumentList.convertArgumentToNeedType(Double::valueOf));
        return action;
    }
}
