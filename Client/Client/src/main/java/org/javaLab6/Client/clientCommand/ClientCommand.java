package org.javaLab6.Client.clientCommand;

import lombok.Getter;
import lombok.Setter;
import org.javaLab6.Client.utils.command.CommandArgumentList;

@Setter
@Getter
public abstract class ClientCommand {
    public CommandArgumentList input(CommandArgumentList args){
        return args;
    }
}
