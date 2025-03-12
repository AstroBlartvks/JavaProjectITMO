package org.AstrosLab.command;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommandArgumentList {
    private final List<CommandArgument> argList = new ArrayList<CommandArgument>();

    public void addArgument(CommandArgument arg){
        this.argList.add(arg);
    }

    public CommandArgumentList getElementArguments(){
        CommandArgumentList elementArgs = new CommandArgumentList();
        this.argList.subList(2, this.argList.size()).forEach(elementArgs::addArgument);
        return elementArgs;
    }

    public int length(){
        return argList.size();
    }

    public CommandArgument getCommand(){
        return getArgumentByIndex(0);
    }

    public CommandArgument getFirstArgument(){
        return getArgumentByIndex(1);
    }

    public CommandArgument getArgumentByIndex(int index){
        return argList.get(index);
    }

    @Override
    public String toString(){
        return argList.toString();
    }
}
