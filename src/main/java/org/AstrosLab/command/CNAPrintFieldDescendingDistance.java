package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

import java.util.ArrayList;

public class CNAPrintFieldDescendingDistance extends Command{
    public CNAPrintFieldDescendingDistance(customCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandNoArguments;
        this.rowCount = 1;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        return "";
    }

    @Override
    public String description() {
        return "print_field_descending_distance: print the values of the distance field of all elements in descending order.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}

