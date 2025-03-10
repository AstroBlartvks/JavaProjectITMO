package org.AstrosLab.command;

import org.AstrosLab.collection.CustomCollection;

import java.util.ArrayList;

public class CNASave extends Command{
    public CNASave(CustomCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandNoArguments;
        this.rowCount = 1;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        clearException();
        return "Json file saved by: 'path'";
    }

    @Override
    public String description() {
        return "save: \n\tsave the collection to a file.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        clearException();
        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}

