package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CMAAddIfMax extends Command{
    public CMAAddIfMax(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return "";
    }

    @Override
    public String description() {
        return "add_if_max {element}: add a new item to a collection if its value exceeds the value of the largest item in that collection.\n";
    }
}

