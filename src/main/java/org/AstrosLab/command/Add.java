package org.AstrosLab.command;

import org.AstrosLab.inputManager.CommandArgumentList;

public class Add extends Command{
    @Override
    public String execute(CommandArgumentList args) throws Exception {
        return "";
    }

    @Override
    public String description() {
        return "add {element}: \n\tadd a new item to the collection.\n";
    }

    @Override
    public CommandArgumentList input() {
        return null;
    }
}
