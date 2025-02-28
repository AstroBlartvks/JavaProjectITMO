package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CNAPrintFieldDescendingDistance extends Command{
    public CNAPrintFieldDescendingDistance(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return "";
    }

    @Override
    public String description() {
        return "print_field_descending_distance: print the values of the distance field of all elements in descending order.\n";
    }
}

