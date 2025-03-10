package org.AstrosLab.command;

import org.AstrosLab.collection.CustomCollection;

import java.util.ArrayList;

public class CILCountByDistance extends Command{
    public CILCountByDistance(CustomCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandInLine;
        this.rowCount = 1;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        clearException();
        double distance = Integer.parseInt(strCommandInLine.get(0).split(" ")[1]);
        return "The routes with 'distance'="+distance+": " + this.collection.countByDistance(distance) + " in collection";
    }

    @Override
    public String description() {
        return "count_by_distance distance: \n\tprint the number of elements with the value of the distance field equal to the specified one.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        clearException();

        if (strCommandInLine.split(" ").length != 2){
            this.error = new UnexpectedCommandException("Сommand 'count_by_distance' must have inline argument 'distance' ex: 'count_by_distance 1488'");
            return null;
        }

        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}

