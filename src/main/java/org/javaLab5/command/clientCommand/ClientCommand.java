package org.javaLab5.command.clientCommand;

import lombok.Getter;
import lombok.Setter;
import org.javaLab5.command.CommandArgumentList;

@Setter
@Getter
public abstract class ClientCommand {
    CommandArgumentList argumentList = new CommandArgumentList();

    public CommandArgumentList input() throws Exception{
        return argumentList;
    }

    public void createArgumentListForInput(CommandArgumentList args){
        this.argumentList = args;
    }
}
