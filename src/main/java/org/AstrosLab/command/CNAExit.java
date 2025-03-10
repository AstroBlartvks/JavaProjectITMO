package org.AstrosLab.command;

import org.AstrosLab.collection.CustomCollection;

import java.util.ArrayList;

public class CNAExit extends Command{
    public CNAExit(CustomCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandNoArguments;
        this.rowCount = 1;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        clearException();
        return "!#CMD:Exit";
    }

    @Override
    public String description() {
        return "exit: \n\tterminate the program (without saving to a file).\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        clearException();
        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}

