package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CNAClear extends Command{
    public CNAClear(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        this.collection.clear();
        return "";
    }

    @Override
    public String description() {
        return "clear: clear the collection.\n";
    }
}

