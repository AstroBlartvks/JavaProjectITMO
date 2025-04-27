package org.AstroLabClient.clientCommand;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.utils.command.CommandArgumentList;

@Setter
@Getter
public abstract class ClientCommand {
    public CommandArgumentList input(CommandArgumentList args){
        return args;
    }
}
