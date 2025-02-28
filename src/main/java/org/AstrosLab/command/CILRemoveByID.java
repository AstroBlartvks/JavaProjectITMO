package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CILRemoveByID extends Command{
    public CILRemoveByID(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return "";
    }

    @Override
    public String description() {
        return "remove_by_id id: delete an item from the collection by its id.\n";
    }
}

