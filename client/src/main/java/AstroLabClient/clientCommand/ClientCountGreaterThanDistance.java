package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionCountGreaterThanDistance;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.utils.command.CommandArgumentList;

public class ClientCountGreaterThanDistance extends ClientCommand {
    @Override
    public ClientServerAction input(CommandArgumentList argumentList) {
        ActionCountGreaterThanDistance action = new ActionCountGreaterThanDistance();
        action.setDistance(argumentList.convertArgumentToNeedType(Double::valueOf));
        return action;
    }
}
