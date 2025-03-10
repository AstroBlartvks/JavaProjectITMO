package org.AstrosLab.inputManager;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class CommandArgumentList {
    private ArrayList<CommandArgument> argList = new ArrayList<CommandArgument>();

    public void addArgument(CommandArgument arg){
        argList.add(arg);
    }
}
