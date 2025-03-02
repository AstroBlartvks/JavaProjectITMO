package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

import java.util.ArrayList;

public class CILCountGreaterThanDistance extends Command{
    public CILCountGreaterThanDistance(customCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandInLine;
        this.rowCount = 1;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        clearException();
        double distance = Integer.parseInt(strCommandInLine.get(0).split(" ")[1]);
        return  "The routes with 'distance' > "+distance+": " + this.collection.greaterThanDistance(distance) + " in collection";
    }

    @Override
    public String description() {
        return "count_greater_than_distance distance: \n\tprint the number of elements whose value in the distance field is greater than the specified value.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        clearException();

        if (strCommandInLine.split(" ").length != 2){
            this.error = new UnexpectedCommandException("Сommand 'count_greater_than_distance' must have inline argument 'distance' ex: 'count_greater_than_distance 2'");
            return null;
        }

        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}



