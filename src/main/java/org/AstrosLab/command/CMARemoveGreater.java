package org.AstrosLab.command;

import org.AstrosLab.collection.CustomCollection;

import java.util.ArrayList;

public class CMARemoveGreater extends Command{
    public CMARemoveGreater(CustomCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandMoreArgument;
        this.rowCount = 5;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        clearException();
        return "";
    }

    @Override
    public String description() {
        return "remove_greater {element}: \n\tdelete all items from the collection that exceed the specified size.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        clearException();
        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}

