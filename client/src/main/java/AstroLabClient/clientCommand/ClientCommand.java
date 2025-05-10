package AstroLabClient.clientCommand;

import AstroLab.actions.components.Action;
import AstroLab.utils.command.CommandArgumentList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class ClientCommand {
    public abstract Action input(CommandArgumentList args);
}
