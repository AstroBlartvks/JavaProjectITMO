package org.AstrosLab.command;

import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.inputManager.CommandArgumentList;

public class Info extends Command{
    private CustomCollection collection;
    
    public Info(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public String execute(CommandArgumentList args) throws Exception {
        return this.collection.toString();
    }

    @Override
    public String description() {
        return "info: \n\toutputs information about the collection (type, initialization date, number of items, etc.) to the standard output stream.\n";
    }

    @Override
    public CommandArgumentList input() {
        return null;
    }
}
