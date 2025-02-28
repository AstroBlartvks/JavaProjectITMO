package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CMAAdd extends Command{
    public CMAAdd(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return "";
    }

    @Override
    public String description() {
        return "add {element}: add a new item to the collection.\n";
    }
}

