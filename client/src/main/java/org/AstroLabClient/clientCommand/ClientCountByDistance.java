package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionCountByDistance;
import org.AstroLab.utils.command.CommandArgumentList;

public class ClientCountByDistance extends ClientCommand{
    @Override
    public Action input(CommandArgumentList argumentList){
        ActionCountByDistance action = new ActionCountByDistance();
        action.setDistance(argumentList.convertArgumentToNeedType(Double::valueOf));
        return action;
    }
}
