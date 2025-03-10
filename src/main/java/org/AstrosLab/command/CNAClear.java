package org.AstrosLab.command;

import org.AstrosLab.collection.CustomCollection;

import java.util.ArrayList;

public class CNAClear extends Command{
    public CNAClear(CustomCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandNoArguments;
        this.rowCount = 1;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        clearException();
        this.collection.clear();
        return "Collection has been cleared!";
    }

    @Override
    public String description() {
        return "clear: \n\tclear the collection.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        clearException();
        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}

