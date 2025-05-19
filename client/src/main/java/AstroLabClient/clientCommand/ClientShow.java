package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionShow;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.auth.UserDTO;
import AstroLab.utils.command.CommandArgumentList;

public class ClientShow extends ClientCommand {
    public ClientShow(UserDTO userDTO) {
        super(userDTO);
    }

    @Override
    public ClientServerAction input(CommandArgumentList args) {
        return new ActionShow(this.userDTO.getLogin(), this.userDTO.getPassword());
    }
}
