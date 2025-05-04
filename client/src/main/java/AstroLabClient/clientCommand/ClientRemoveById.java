package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionRemoveById;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.utils.command.CommandArgumentList;

public class ClientRemoveById extends ClientCommand {
    @Override
    public ClientServerAction input(CommandArgumentList argumentList) {
        ActionRemoveById action = new ActionRemoveById();
        action.setId(argumentList.convertArgumentToNeedType(Integer::valueOf));
        return action;
    }
}
