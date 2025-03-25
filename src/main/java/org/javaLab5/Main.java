package org.javaLab5;

import org.javaLab5.collection.CustomCollection;

import org.javaLab5.command.clientCommand.ClientCommand;
import org.javaLab5.inputManager.ArgumentRequester;
import org.javaLab5.inputManager.CommandIdentifier;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.command.clientCommand.UndefindCommandException;
import org.javaLab5.command.serverCommand.CommandManager;
import org.javaLab5.command.serverCommand.ResponseStatus;
import org.javaLab5.command.serverCommand.ServerResponse;
import org.javaLab5.files.JsonReader;
import org.javaLab5.files.Reader;


import org.javaLab5.inputManager.NewScannerManager;
//execute_script src/resources/scripts/script1.sc

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

        NewScannerManager newScannerManager = new NewScannerManager();
        CommandManager commandManager = new CommandManager(collection);
        CommandIdentifier commandIndent = new CommandIdentifier(newScannerManager);
        ArgumentRequester.setNewScannerManager(newScannerManager);

        while (newScannerManager.hasNextLine()) {
            //User
            String input = newScannerManager.readLine();
            ClientCommand clientCommand;
            CommandArgumentList commandArgList;

            try {
                clientCommand = commandIndent.getCommand(input);
            } catch (UndefindCommandException e) {
                System.out.println("Exception: " + e);
                continue;
            } catch (Exception e){
                System.out.println("Unexpected exception: '" + e);
                continue;
            }

            if (clientCommand == null) {
                System.out.println("Unexpected command: '" + input + "'. Try write 'help'");
                continue;
            }

            try {
                commandArgList = clientCommand.input();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                continue;
            }

            if (commandArgList == null){
                continue;
            }

            //Server
            try {
                ServerResponse response = commandManager.executeCommand(commandArgList);
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
