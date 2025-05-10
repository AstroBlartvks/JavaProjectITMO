package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionAddIfMax;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.utils.command.CommandArgumentList;
import AstroLabClient.inputManager.ArgumentRequester;
import AstroLabClient.inputManager.SystemInClosedException;

public class ClientAddIfMax extends ClientCommand {
    private final ArgumentRequester argumentRequester;

    public ClientAddIfMax(ArgumentRequester argumentRequester) {
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
        ActionAddIfMax action = new ActionAddIfMax();
        action.setCreateRouteDto(RouteDtoParser.parse(this.argumentRequester));
        return action;
    }
}
