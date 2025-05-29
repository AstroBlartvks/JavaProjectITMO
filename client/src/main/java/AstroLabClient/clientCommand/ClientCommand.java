package AstroLabClient.clientCommand;

import AstroLab.actions.components.Action;
import AstroLab.auth.UserDTO;
import AstroLab.utils.command.CommandArgumentList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class ClientCommand {
    protected final UserDTO userDTO;

    public ClientCommand(UserDTO userDTO){
        this.userDTO = userDTO;
    }

    public abstract Action input(CommandArgumentList args);
}
