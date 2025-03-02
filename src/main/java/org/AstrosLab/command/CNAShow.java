package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

import java.util.ArrayList;
import java.util.Scanner;

public class CNAShow extends Command{
    public CNAShow(customCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandNoArguments;
        this.rowCount = 1;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        return this.collection.getRoutesDescriptions();
    }

    @Override
    public String description() {
        return "show: outputs all the elements of the collection in a string representation to the standard output stream.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}
