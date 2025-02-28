package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;
import java.util.ArrayList;
import java.util.HashMap;

public class CNAHelp extends Command{
    ArrayList<Command> commands = new ArrayList<Command>();
    public CNAHelp(customCollection externCollection, HashMap<String, Command>  commandListing){
        this.collection = externCollection;
        this.setCommands(commandListing);
    }

    private void setCommands(HashMap<String, Command>  commandListing){
        this.commands.addAll(commandListing.values());
    }

    @Override
    public String execute(String commandText) {
        StringBuilder text = new StringBuilder();
        for (Command cmd : this.commands){
            text.append(cmd.description());
        }
        return (String)(this.description() + text);
    }

    @Override
    public String description() {
        return "help: displays help for available commands.\n";
    }
}

