package org.AstrosLab.command;

import org.AstrosLab.collection.CustomCollection;

import java.util.ArrayList;

public class CILRemoveByID extends Command{

    public CILRemoveByID(CustomCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandInLine;
        this.rowCount = 1;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        clearException();
        int id = Integer.parseInt(strCommandInLine.get(0).split(" ")[1]);

        if (!this.collection.containsID(id)){
            return "Collection have no <Route> with 'id'=" + id;
        }

        this.collection.removeByID(id);

        return "Route with 'id'=" + id + " has removed!";
    }

    @Override
    public String description() {
        return "remove_by_id id: \n\tdelete an item from the collection by its id.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        clearException();

        if (strCommandInLine.split(" ").length != 2){
            this.error = new UnexpectedCommandException("Сommand 'remove_by_id' must have inline argument 'id' ex: 'remove_by_id 2'");
            return null;
        }

        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}

