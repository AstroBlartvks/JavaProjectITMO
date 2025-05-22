package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionRemoveById;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.auth.UserDTO;
import AstroLab.utils.command.CommandArgumentList;

public class ClientRemoveById extends ClientCommand {
    public ClientRemoveById(UserDTO userDTO) {
        super(userDTO);
    }

    @Override
    public ClientServerAction input(CommandArgumentList argumentList) {
        ActionRemoveById action = new ActionRemoveById(this.userDTO.getLogin(), this.userDTO.getPassword());
        action.setId(argumentList.convertArgumentToNeedType(Integer::valueOf));
        return action;
    }
}
