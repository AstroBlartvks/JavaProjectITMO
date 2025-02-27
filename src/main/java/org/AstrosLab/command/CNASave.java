package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CNASave extends Command{
    public CNASave(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return "";
    }

    @Override
    public String description() {
        return "save: save the collection to a file.\n";
    }
}

