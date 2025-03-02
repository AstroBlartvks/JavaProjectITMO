package org.AstrosLab;

import org.AstrosLab.collectrion.customCollection;

import org.AstrosLab.command.*;

import org.AstrosLab.files.JSonReader;
import org.AstrosLab.files.Reader;
import org.AstrosLab.inputManager.InputManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        customCollection collect = new customCollection();

        Reader read = new Reader(new JSonReader());
        collect = read.readFromEnv("JAVATESTFILE");

        if (read.getException() != null || collect == null){
            System.err.println("Errors were found in the file, fix them: \n" + read.getException());
            return;
        }

        HashMap<String, Command> commandListing = new HashMap<String, Command>();
        commandListing.put("show", new CNAShow(collect));
        commandListing.put("info", new CNAInfo(collect));
        commandListing.put("clear", new CNAClear(collect));
        commandListing.put("save", new CNASave(collect));
        commandListing.put("exit", new CNAExit(collect));
        commandListing.put("print_field_descending_distance", new CNAPrintFieldDescendingDistance(collect));

        commandListing.put("remove_by_id", new CILRemoveByID(collect));
        commandListing.put("count_by_distance", new CILCountByDistance(collect));
        commandListing.put("count_greater_than_distance", new CILCountGreaterThanDistance(collect));
        commandListing.put("execute_script", new CILExecuteScript(collect));

        commandListing.put("add", new CMAAdd(collect));
        commandListing.put("add_if_max", new CMAAddIfMax(collect));
        commandListing.put("add_if_min", new CMAAddIfMin(collect));
        commandListing.put("update", new CMAUpdate(collect));
        commandListing.put("remove_greater", new CMARemoveGreater(collect));

        commandListing.put("help", new CNAHelp(collect, commandListing));

        CommandManager cmdManager = new CommandManager(commandListing);
        InputManager inputManager = new InputManager(commandListing);


        while (true) {
            ArrayList<String> cmdString = inputManager.input();
            if (inputManager.getException() != null || cmdString == null) {
                System.out.println("You got Exception: " + inputManager.getException());
                continue;
            }

            String result = cmdManager.exec(cmdString);

            if (cmdManager.getException() != null) {
                System.out.println("Something went wrong in executing: " + cmdManager.getException());
                continue;
            } else if (Objects.equals(result, "!#CMD:Exit")){
                break;
            }

            System.out.println(result);
        }
        System.out.println("Programm closed!");
    }
}
