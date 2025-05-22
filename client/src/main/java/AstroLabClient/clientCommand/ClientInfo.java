package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionInfo;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.auth.UserDTO;
import AstroLab.utils.command.CommandArgumentList;

public class ClientInfo extends ClientCommand {
    public ClientInfo(UserDTO userDTO) {
        super(userDTO);
    }

    @Override
    public ClientServerAction input(CommandArgumentList args) {
        return new ActionInfo(this.userDTO.getLogin(), this.userDTO.getPassword());
    }
}
