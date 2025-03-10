package org.AstrosLab.command;

import org.AstrosLab.collection.CustomCollection;

import java.util.ArrayList;

public class CNAPrintFieldDescendingDistance extends Command{
    public CNAPrintFieldDescendingDistance(CustomCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandNoArguments;
        this.rowCount = 1;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        clearException();
        return "Reversed distances:\n"+this.collection.printFieldDescendingDistance();
    }

    @Override
    public String description() {
        return "print_field_descending_distance: \n\tprint the values of the distance field of all elements in descending order.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        clearException();
        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}

