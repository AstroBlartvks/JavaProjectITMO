package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionCountGreaterThanDistance;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.auth.UserDTO;
import AstroLab.utils.command.CommandArgumentList;

public class ClientCountGreaterThanDistance extends ClientCommand {
    public ClientCountGreaterThanDistance(UserDTO userDTO) {
        super(userDTO);
    }

    @Override
    public ClientServerAction input(CommandArgumentList argumentList) {
        ActionCountGreaterThanDistance action = new ActionCountGreaterThanDistance(this.userDTO.getLogin(), this.userDTO.getPassword());
        action.setDistance(argumentList.convertArgumentToNeedType(Double::valueOf));
        return action;
    }
}
