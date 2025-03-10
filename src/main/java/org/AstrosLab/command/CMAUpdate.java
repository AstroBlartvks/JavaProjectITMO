package org.AstrosLab.command;

import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.model.Route;

import java.util.ArrayList;

public class CMAUpdate extends Command{
    public CMAUpdate(CustomCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandMoreArgument;
        this.rowCount = 5;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        clearException();
        int id = Integer.parseInt(strCommandInLine.get(0).split(" ")[1]);

        if (!this.collection.containsID(id)){
            return "Collection have no <Route> with 'id'=" + id;
        }

        try{
            Route route = CreateRoute.create(id, strCommandInLine);
            this.collection.removeByID(id);
            this.collection.addElement(route);
        } catch (Exception e){
            return e.toString();
        }
        return "Route with 'id'=" + id + " has changed successfully!";
    }

    @Override
    public String description() {
        return "update id {element}: \n\tupdate the value of a collection item whose id is equal to the specified one.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        clearException();
        ArrayList<String> inputResult;

        if (strCommandInLine.split(" ").length != 2){
            this.error = new UnexpectedCommandException("Command 'update' must have inline argument 'id' ex: 'update 2'");
            return null;
        }

        try {
            inputResult = InputRoute.input(strCommandInLine);
            return inputResult;
        } catch (Exception e){
            this.error = e;
            return null;
        }
    }
}

