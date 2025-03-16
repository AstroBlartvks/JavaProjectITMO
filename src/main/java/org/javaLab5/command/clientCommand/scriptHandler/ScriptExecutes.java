package org.javaLab5.command.clientCommand.scriptHandler;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.clientCommand.ClientCommand;
import org.javaLab5.command.clientCommand.CommandIdentifier;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.command.serverCommand.CommandManager;
import org.javaLab5.command.serverCommand.ResponseStatus;
import org.javaLab5.command.serverCommand.ServerResponse;
import org.javaLab5.inputManager.InputManager;
import org.javaLab5.inputManager.ScannerManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Handles the execution of script files, ensuring proper command execution
 * while preventing recursion loops.
 */
public class ScriptExecutes {
    private static final Stack<String> scriptStack = new Stack<>();

    //Временная коллекция уберу когда будет работать логика сервер-клиент
    public static CustomCollection collection;

    /**
     * Executes a script file by reading and processing each line as a command.
     *
     * @param scriptName The name of the script file to execute.
     * @throws Exception If the script file does not exist, is unreadable, or recursion is detected.
     */
    public void execute(String scriptName) throws Exception {
        //Временная коллекция уберу когда будет работать логика сервер-клиент
        CommandManager commandManager = new CommandManager(collection);

        if (!new File(scriptName).exists()) {
            throw new Exception("Script-file '" + scriptName + "' doesn't exist!");
        }
        if (!Files.isReadable(Paths.get(scriptName))) {
            throw new Exception("Insufficient permissions to read the '" + scriptName + "' script-file");
        }

        if (scriptStack.contains(scriptName)) {
            String fileSeq = scriptStack.toString();
            scriptStack.clear();
            ScannerManager.closeFileScanner();
            ScannerManager.setConsoleScanner();
            throw new RecursionDetectedException("Recursion in files detected: " + fileSeq + "!");
        }

        try (Scanner scriptScanner = new Scanner(new File(scriptName))) {
            scriptStack.add(scriptName);
            ScannerManager.setFileScanner(scriptScanner);
            ScannerManager.setMainFileScanner();

            // User input handling
            InputManager inputManager = new InputManager();
            CommandIdentifier commandIndent = new CommandIdentifier();

            while (scriptScanner.hasNextLine()) {
                String input = inputManager.input();
                ClientCommand clientCommand;
                CommandArgumentList commandArgList;

                System.out.println(">>> " + input);
                try {
                    clientCommand = commandIndent.getCommand(input);
                } catch (Exception e) {
                    System.out.println("Command: '" + input + "' doesn't exist, check 'help'");
                    continue;
                }

                if (clientCommand == null) {
                    System.out.println("Unexpected command: '" + input + "'. Try writing 'help'");
                    continue;
                }

                try {
                    commandArgList = clientCommand.input(input);
                } catch (RecursionDetectedException e) {
                    throw e;
                } catch (Exception e) {
                    System.out.println("Exception: " + e);
                    continue;
                }

                if (commandArgList == null) {
                    continue;
                }

                //Временно уберу когда будет работать логика сервер-клиент
                try {
                    ServerResponse response = commandManager.executeCommand(commandArgList);
                    if (response.getStatus() == ResponseStatus.EXIT) {
                        System.exit(0);
                    }
                    System.out.println(response);
                } catch (Exception e) {
                    ServerResponse response = new ServerResponse(ResponseStatus.EXCEPTION, e);
                    System.out.println(response);
                }
            }
            scriptStack.pop();
            ScannerManager.setConsoleScanner();
        } catch (FileNotFoundException exception) {
            throw new Exception("Script-file '" + scriptName + "' doesn't exist!");
        } catch (NoSuchElementException exception) {
            throw new Exception("Script-file '" + scriptName + "' is empty!");
        } catch (IllegalStateException exception) {
            throw new Exception("Unexpected error!");
        }
    }
}
