package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionHelp;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.auth.UserDTO;
import AstroLab.utils.command.CommandArgumentList;

public class ClientHelp extends ClientCommand {
    public ClientHelp(UserDTO userDTO) {
        super(userDTO);
    }

    @Override
    public ClientServerAction input(CommandArgumentList args) {
        return new ActionHelp(this.userDTO.getLogin(), this.userDTO.getPassword());
    }
}
