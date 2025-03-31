package org.javaLab5.command.clientCommand;

import lombok.Getter;
import lombok.Setter;
import org.javaLab5.command.CommandArgumentList;

@Setter
@Getter
public abstract class ClientCommand {
    public CommandArgumentList input(CommandArgumentList args){
        return args;
    }
}
