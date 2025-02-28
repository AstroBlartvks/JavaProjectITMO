package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CMARemoveGreater extends Command{
    public CMARemoveGreater(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return "";
    }

    @Override
    public String description() {
        return "remove_greater {element}: delete all items from the collection that exceed the specified size.\n";
    }
}

