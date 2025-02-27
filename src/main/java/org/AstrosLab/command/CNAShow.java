package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CNAShow extends Command{
    public CNAShow(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return this.collection.getRoutesDescriptions();
    }

    @Override
    public String description() {
        return "show: outputs all the elements of the collection in a string representation to the standard output stream.\n";
    }
}
