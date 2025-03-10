package org.AstrosLab.command;

import org.AstrosLab.collection.CustomCollection;

import java.util.ArrayList;

public class CILExecuteScript extends Command{
    public CILExecuteScript(CustomCollection externCollection){
        this.collection = externCollection;
        this.type = CMDTypes.CommandInLine;
        this.rowCount = 1;
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        clearException();
        return "";
    }

    @Override
    public String description() {
        return "execute_script filename: \n\tread and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user interactively.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        clearException();
        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}

