package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionUpdate;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.utils.command.CommandArgumentList;
import AstroLabClient.inputManager.ArgumentRequester;
import AstroLabClient.inputManager.SystemInClosedException;

public class ClientUpdate extends ClientCommand {
    private final ArgumentRequester argumentRequester;

    public ClientUpdate(ArgumentRequester argumentRequester) {
        this.argumentRequester = argumentRequester;
    }

    @Override
    public ClientServerAction input(CommandArgumentList argumentList)
            throws IllegalArgumentException, SystemInClosedException {
        if (argumentList.getFirstArgument() == null) {
            throw new IllegalArgumentException("The 'update' command has syntax and must contain the " +
                    "'id' argument example: 'update id {element}'");
        }

        ActionUpdate action = new ActionUpdate();
        action.setId(argumentList.convertArgumentToNeedType(Integer::valueOf));
        action.setCreateRouteDto(RouteDtoParser.parse(this.argumentRequester));
        return action;
    }
}
