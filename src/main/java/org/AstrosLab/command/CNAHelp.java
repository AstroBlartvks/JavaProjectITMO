package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;
import java.util.ArrayList;

public class CNAHelp extends Command{
    ArrayList<Command> commands = new ArrayList<Command>();
    public CNAHelp(customCollection externCollection){
        this.collection = externCollection;
    }

    public void setCommands(Command cmd){
        this.commands.add(cmd);
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

