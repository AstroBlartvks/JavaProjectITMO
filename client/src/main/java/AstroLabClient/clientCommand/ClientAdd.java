package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionAdd;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.utils.command.CommandArgumentList;
import AstroLabClient.inputManager.ArgumentRequester;
import AstroLabClient.inputManager.SystemInClosedException;

public class ClientAdd extends ClientCommand {
    private final ArgumentRequester argumentRequester;

    public ClientAdd(ArgumentRequester argumentRequester) {
        this.argumentRequester = argumentRequester;
    }

    /**.
     * Command 'Add'
     *
     * @return CommandArgumentList arguments of command
     */
    @Override
    public ClientServerAction input(CommandArgumentList argumentList)
            throws IllegalArgumentException, SystemInClosedException {
        ActionAdd action = new ActionAdd();
        action.setCreateRouteDto(RouteDtoParser.parse(this.argumentRequester));
        return action;
    }
}
