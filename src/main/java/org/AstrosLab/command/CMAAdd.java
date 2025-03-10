package org.AstrosLab.command;

import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.model.Route;

import java.util.ArrayList;

public class CMAAdd extends Command{
    public CMAAdd(CustomCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandMoreArgument;
        this.rowCount = 5;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        clearException();
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
        clearException();
        ArrayList<String> inputResult;
        try {
            inputResult = InputRoute.input(strCommandInLine);
            return inputResult;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}

