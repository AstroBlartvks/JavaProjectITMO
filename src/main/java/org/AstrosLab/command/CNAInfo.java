package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CNAInfo extends Command{
    public CNAInfo(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return this.collection.toString();
    }

    @Override
    public String description() {
        return "info: outputs information about the collection (type, initialization date, number of items, etc.) to the standard output stream.\n";
    }
}

