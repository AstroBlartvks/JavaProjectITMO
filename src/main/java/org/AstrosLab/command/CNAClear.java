package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

import java.util.ArrayList;

public class CNAClear extends Command{
    public CNAClear(customCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandNoArguments;
        this.rowCount = 1;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        this.collection.clear();
        return "";
    }

    @Override
    public String description() {
        return "clear: clear the collection.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}

