package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionCountByDistance;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.auth.UserDTO;
import AstroLab.utils.command.CommandArgumentList;

public class ClientCountByDistance extends ClientCommand {
    public ClientCountByDistance(UserDTO userDTO) {
        super(userDTO);
    }

    @Override
    public ClientServerAction input(CommandArgumentList argumentList) {
        ActionCountByDistance action = new ActionCountByDistance(this.userDTO.getLogin(), this.userDTO.getPassword());
        action.setDistance(argumentList.convertArgumentToNeedType(Double::valueOf));
        return action;
    }
}
