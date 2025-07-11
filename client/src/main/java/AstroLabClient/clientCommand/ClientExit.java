package AstroLabClient.clientCommand;

import AstroLab.auth.UserDTO;
import AstroLab.utils.command.CommandArgumentList;
import AstroLabClient.clientAction.ActionExit;
import AstroLab.actions.components.ClientAction;

public class ClientExit extends ClientCommand {
    public ClientExit(UserDTO userDTO) {
        super(userDTO);
    }

    @Override
    public ClientAction input(CommandArgumentList args) {
        return new ActionExit();
    }
}
