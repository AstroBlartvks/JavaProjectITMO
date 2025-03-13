package org.AstrosLab;

import org.AstrosLab.collection.CustomCollection;

import org.AstrosLab.command.ClientCommand.ClientCommand;
import org.AstrosLab.command.ClientCommand.CommandIdentifier;
import org.AstrosLab.command.CommandArgumentList;
import org.AstrosLab.command.ServerCommand.CommandManager;
import org.AstrosLab.command.ServerCommand.ResponseStatus;
import org.AstrosLab.command.ServerCommand.ServerResponse;
import org.AstrosLab.files.JsonReader;
import org.AstrosLab.files.Reader;
import org.AstrosLab.inputManager.InputManager;

//Уберу после разделения на сервер и клиент
import org.AstrosLab.command.ClientCommand.ScriptHandler.ScriptExecuter;
//execute_script src/resources/scripts/script1.sc
//execute_script src/resources/scripts/script2.sc

//Pray to god that this works
public class Main {
    public static void main(String[] args) {
        CustomCollection collection = new CustomCollection();

        Reader reader = new Reader(new JsonReader());
        try {
            collection = reader.readFromEnv();
        } catch (Exception e) {
            System.out.println("Ops... Program got some exception(s) while reading!\n" + e);
            System.exit(-1);
        }

        //Уберу после разделения на сервер и клиент
        ScriptExecuter.collection = collection;

        //Handle
        CommandManager commandManager = new CommandManager(collection);

        //User
        InputManager inputManager = new InputManager();
        CommandIdentifier commandIndent = new CommandIdentifier();


        while (inputManager.hasNextInput()) {
            //User
            String input = inputManager.input();
            ClientCommand clientCommand;
            CommandArgumentList commandArgList;

            try {
                clientCommand = commandIndent.getCommand(input);
            } catch (Exception e) {
                System.out.println("Command: '" + input + "' doesn't exist, check 'help'");
                continue;
            }

            if (clientCommand == null) {
                System.out.println("Unexpected command: '" + input + "'. Try write 'help'");
                continue;
            }

            try {
                commandArgList = clientCommand.input(input);
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                continue;
            }

            if (commandArgList == null){
                continue;
            }

            //Handle
            try {
                ServerResponse response = commandManager.executeComand(commandArgList);
                if (response.getStatus() == ResponseStatus.EXIT){
                    System.exit(0);
                }
                System.out.println(response);
            } catch (Exception e) {
                ServerResponse response = new ServerResponse(ResponseStatus.EXCEPTION, e);
                System.out.println(response);
            }
        }
    }
}
