package org.AstrosLab.command;

import org.AstrosLab.collectrion.customCollection;

public class CNAExit extends Command{
    public CNAExit(customCollection externCollection){
        this.collection = externCollection;
    }

    @Override
    public String execute(String commandText) {
        return "Exit";
    }

    @Override
    public String description() {
        return "exit: terminate the program (without saving to a file).\n";
    }
}

