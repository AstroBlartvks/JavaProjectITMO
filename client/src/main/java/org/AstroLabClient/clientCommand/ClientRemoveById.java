package org.AstroLabClient.clientCommand;

import org.AstroLab.actions.components.Action;
import org.AstroLab.actions.components.ActionRemoveById;
import org.AstroLab.utils.command.CommandArgumentList;

public class ClientRemoveById extends ClientCommand {
    @Override
    public Action input(CommandArgumentList argumentList){
        ActionRemoveById action = new ActionRemoveById();
        action.setId(argumentList.convertArgumentToNeedType(Integer::valueOf));
        return action;
    }
}
