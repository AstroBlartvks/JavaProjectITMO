package org.AstrosLab.command;

import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.inputManager.CommandArgumentList;

public class Show extends Command{
    private CustomCollection collection;

    public Show(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public String execute(CommandArgumentList args) throws Exception {
        return this.collection.getRoutesDescriptions();
    }

    @Override
    public String description() {
        return "show: \n\toutputs all the elements of the collection in a string representation to the standard output stream.\n";
    }

    @Override
    public CommandArgumentList input() {

        return null;
    }
}
