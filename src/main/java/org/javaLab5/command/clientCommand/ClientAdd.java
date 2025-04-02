package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.inputManager.ArgumentRequester;
import org.javaLab5.inputManager.SystemInClosedException;


public class ClientAdd extends ClientCommand{
    private final ArgumentRequester argumentRequester;

    public ClientAdd(ArgumentRequester argumentRequester){
        this.argumentRequester = argumentRequester;
    }
    /**
     * Command 'Add'
     * @return CommandArgumentList arguments of command
     */
    @Override
    public CommandArgumentList input(CommandArgumentList argumentList) throws IllegalArgumentException, SystemInClosedException {
        argumentList.addArgument(new CommandArgument(RouteDTOParser.parse(this.argumentRequester)));
        return argumentList;
    }
}
