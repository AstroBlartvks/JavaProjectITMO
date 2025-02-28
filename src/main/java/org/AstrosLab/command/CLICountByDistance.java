package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CLICountByDistance extends Command{
    public CLICountByDistance(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return "";
    }

    @Override
    public String description() {
        return "count_by_distance distance: print the number of elements with the value of the distance field equal to the specified one.\n";
    }
}

