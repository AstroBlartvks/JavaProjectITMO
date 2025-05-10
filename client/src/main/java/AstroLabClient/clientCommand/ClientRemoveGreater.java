package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionRemoveGreater;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.utils.command.CommandArgumentList;
import AstroLabClient.inputManager.ArgumentRequester;
import AstroLabClient.inputManager.SystemInClosedException;

public class ClientRemoveGreater extends ClientCommand {
    private final ArgumentRequester argumentRequester;

    public ClientRemoveGreater(ArgumentRequester argumentRequester) {
        this.argumentRequester = argumentRequester;
    }

    @Override
    public ClientServerAction input(CommandArgumentList argumentList)
            throws IllegalArgumentException, SystemInClosedException {
        ActionRemoveGreater action = new ActionRemoveGreater();
        action.setCreateRouteDto(RouteDtoParser.parse(this.argumentRequester));
        return action;
    }
}
