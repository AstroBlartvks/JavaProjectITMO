package org.javaLab6;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.javaLab6.collection.CustomCollection;
import org.javaLab6.files.FileIsNotExistException;
import org.javaLab6.files.JsonReader;
import org.javaLab6.files.Reader;
import org.javaLab6.serverCommand.CommandManager;
import org.javaLab6.utils.ClientServer.ResponseStatus;
import org.javaLab6.utils.ClientServer.ServerResponse;
import org.javaLab6.utils.command.CommandArgument;
import org.javaLab6.utils.command.CommandArgumentList;

public final class Server {
    private final CommandManager commandManager;
    private String ip;
    private int port;

    public Server (String ip, int port){
        CustomCollection collection = readCollection();
        this.commandManager = new CommandManager(collection);

        this.ip = ip;
        this.port = port;
    }

    private CustomCollection readCollection() {
        CustomCollection result = null;
        final Reader reader = new Reader(new JsonReader());

        try {
            result = reader.readFromEnv();
        } catch (FileIsNotExistException e) {
            System.out.println("Collection file not found. Initializing empty collection:" + e.getMessage());
            result = new CustomCollection();
        } catch (JsonMappingException e) {
            handleFatalError("Invalid JSON format. Check data structure and syntax.", e);
        } catch (Exception e) {
            handleFatalError("Unexpected error during collection loading.", e);
        }

        return result != null ? result : new CustomCollection();
    }

    private void handleFatalError(String description, Throwable cause) {
        System.err.println("FATAL ERROR: " + description);
        System.err.println("Root cause: " + cause.getMessage());
        System.exit(-1);
    }

    public void run(){
        CommandArgumentList commandArgList = receive();
        ServerResponse response = executeCommand(commandArgList);
        sendResponse(response);
    }

    private CommandArgumentList receive(){
        CommandArgumentList commandArgList = new CommandArgumentList();
        commandArgList.addArgument(new CommandArgument("exit"));
        commandArgList.addArgument(null);

        return commandArgList;
    }

    private ServerResponse executeCommand(CommandArgumentList commandArgList){
        ServerResponse response;
        try {
            response = commandManager.executeCommand(commandArgList);
        } catch (Exception e) {
            response = new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
        if (response == null) response = new ServerResponse(ResponseStatus.EXCEPTION, "Unexpected Exception!");

        return response;
    }

    private void sendResponse(ServerResponse response){
        System.out.println(response);
    }

    public void close(){
        System.out.println("Client has closed!");
    }
}
