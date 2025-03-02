package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

import java.util.ArrayList;

public class CILCountByDistance extends Command{
    public CILCountByDistance(customCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandInLine;
        this.rowCount = 1;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        return "";
    }

    @Override
    public String description() {
        return "count_by_distance distance: print the number of elements with the value of the distance field equal to the specified one.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}

