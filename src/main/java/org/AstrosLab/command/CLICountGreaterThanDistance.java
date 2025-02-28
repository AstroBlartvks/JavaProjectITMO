package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CLICountGreaterThanDistance extends Command{
    public CLICountGreaterThanDistance(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return "";
    }

    @Override
    public String description() {
        return "count_greater_than_distance distance: print the number of elements whose value in the distance field is greater than the specified value.\n";
    }
}

