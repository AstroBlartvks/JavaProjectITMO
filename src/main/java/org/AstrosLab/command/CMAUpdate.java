package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CMAUpdate extends Command{
    public CMAUpdate(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return "";
    }

    @Override
    public String description() {
        return "update id {element}: update the value of a collection item whose id is equal to the specified one.\n";
    }
}

