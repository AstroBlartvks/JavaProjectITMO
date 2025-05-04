package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionCountByDistance;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.utils.command.CommandArgumentList;

public class ClientCountByDistance extends ClientCommand {
    @Override
    public ClientServerAction input(CommandArgumentList argumentList) {
        ActionCountByDistance action = new ActionCountByDistance();
        action.setDistance(argumentList.convertArgumentToNeedType(Double::valueOf));
        return action;
    }
}
