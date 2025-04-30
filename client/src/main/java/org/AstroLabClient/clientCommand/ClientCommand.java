package org.AstroLabClient.clientCommand;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.components.Action;
import org.AstroLab.utils.command.CommandArgumentList;

@Setter
@Getter
public abstract class ClientCommand {
    public abstract Action input(CommandArgumentList args);
}
