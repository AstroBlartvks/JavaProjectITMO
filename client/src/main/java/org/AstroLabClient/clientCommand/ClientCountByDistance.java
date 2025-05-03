package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionCountByDistance;
import org.AstroLab.actions.components.ClientServerAction;
import org.AstroLab.utils.command.CommandArgumentList;

public class ClientCountByDistance extends ClientCommand{
    @Override
    public ClientServerAction input(CommandArgumentList argumentList){
        ActionCountByDistance action = new ActionCountByDistance();
        action.setDistance(argumentList.convertArgumentToNeedType(Double::valueOf));
        return action;
    }
}
