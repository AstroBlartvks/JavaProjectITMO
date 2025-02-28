package org.AstrosLab;

import org.AstrosLab.collectrion.customCollection;

import org.AstrosLab.command.*;

import org.AstrosLab.files.JSonReader;
import org.AstrosLab.files.Reader;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        customCollection collect = new customCollection();

        Reader read = new Reader(new JSonReader());
        collect = read.readFromEnv();

        if (read.getException() != null){
            System.err.println("Errors were found in the file, fix them: \n" + read.getException());
            return;
        }
        //collect.printAll();
        System.out.println();

        HashMap<String, Command> commandListing = new HashMap<String, Command>();
        commandListing.put("show", new CNAShow(collect));
        commandListing.put("info", new CNAInfo(collect));
        commandListing.put("clear", new CNAClear(collect));
        commandListing.put("help", new CNAHelp(collect, commandListing));

        System.out.println(commandListing.get("show").execute("show"));
        System.out.println(commandListing.get("info").execute("info"));
        System.out.println(commandListing.get("clear").execute("clear"));
        System.out.println(commandListing.get("help").execute("help"));
    }
}
