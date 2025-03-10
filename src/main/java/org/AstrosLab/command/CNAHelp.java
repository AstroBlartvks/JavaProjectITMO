package org.AstrosLab.command;

import org.AstrosLab.collection.CustomCollection;
import java.util.ArrayList;
import java.util.HashMap;

public class CNAHelp extends Command{
    ArrayList<Command> commands = new ArrayList<Command>();
    public CNAHelp(CustomCollection externCollection, HashMap<String, Command>  commandListing){
        this.collection = externCollection;
        this.setCommands(commandListing);
        this.type = CMDTypes.CommandNoArguments;
        this.rowCount = 1;
    }

    private void setCommands(HashMap<String, Command>  commandListing){
        this.commands.addAll(commandListing.values());
    }

    @Override
    public String execute(ArrayList<String> strCommandInLine) {
        clearException();
        StringBuilder text = new StringBuilder();
        for (Command cmd : this.commands){
            text.append("\n"+cmd.description());
        }
        return (String)(this.description() + text);
    }

    @Override
    public String description() {
        return "help: \n\tdisplays help for available commands.\n";
    }

    @Override
    public ArrayList<String> input(String strCommandInLine){
        clearException();
        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);
        return response;
    }
}

