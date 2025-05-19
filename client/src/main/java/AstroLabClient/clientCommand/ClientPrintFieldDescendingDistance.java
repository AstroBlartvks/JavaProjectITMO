package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionPrintFieldDescendingDistance;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.auth.UserDTO;
import AstroLab.utils.command.CommandArgumentList;

public class ClientPrintFieldDescendingDistance extends ClientCommand {
    public ClientPrintFieldDescendingDistance(UserDTO userDTO) {
        super(userDTO);
    }

    @Override
    public ClientServerAction input(CommandArgumentList args) {
        return new ActionPrintFieldDescendingDistance(this.userDTO.getLogin(), this.userDTO.getPassword());
    }
}
