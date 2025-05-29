package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionClear;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.auth.UserDTO;
import AstroLab.utils.command.CommandArgumentList;

public class ClientClear extends ClientCommand {
    public ClientClear(UserDTO userDTO) {
        super(userDTO);
    }

    @Override
    public ClientServerAction input(CommandArgumentList args) {
        return new ActionClear(this.userDTO.getLogin(), this.userDTO.getPassword());
    }
}
