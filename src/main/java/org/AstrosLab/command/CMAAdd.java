package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;
import org.AstrosLab.model.Route;

import java.util.ArrayList;

public class CMAAdd extends Command{
    public CMAAdd(customCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandMoreArgument;
        this.rowCount = 5;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        int id = this.collection.getNewID();

        try{
            Route route = CreateRoute.create(id, strCommandInLine);
            this.collection.addElement(route);
        } catch (Exception e){
            return e.toString();
        }

        return "Route created!\n";
    }

    @Override
    public String description() {
        return "add {element}: \n\tadd a new item to the collection.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine) throws Exception {
        ArrayList<String> inputResult;
        try {
            inputResult = inputRoute.input(strCommandInLine);
            return inputResult;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}

