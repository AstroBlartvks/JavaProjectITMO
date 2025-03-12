package org.AstrosLab.command;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class CommandArgumentList {
    private final ArrayList<CommandArgument> argList = new ArrayList<CommandArgument>();
    private final String commandName;

    public CommandArgumentList(String commandName) {
        this.commandName = commandName;
        this.argList.add(new CommandArgument(commandName));
    }

    public void addArgument(CommandArgument arg){
        this.argList.add(arg);
    }
}
