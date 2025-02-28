package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CMAAddIfMin extends Command{
    public CMAAddIfMin(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return "";
    }

    @Override
    public String description() {
        return "add_if_min {element}: add a new item to the collection if its value is less than that of the smallest item in this collection.\n";
    }
}

