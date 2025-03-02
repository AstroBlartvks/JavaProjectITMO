package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

import java.util.ArrayList;

public class CMAAddIfMin extends Command{
    public CMAAddIfMin(customCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandMoreArgument;
        this.rowCount = 5;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        return "";
    }

    @Override
    public String description() {
        return "add_if_min {element}: \n\tadd a new item to the collection if its value is less than that of the smallest item in this collection.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}

