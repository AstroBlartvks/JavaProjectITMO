package org.javaLab6.Client.clientCommand;

import org.javaLab6.Client.utils.command.CommandArgument;
import org.javaLab6.Client.utils.command.CommandArgumentList;
import org.javaLab6.Client.inputManager.ArgumentRequester;
import org.javaLab6.Client.inputManager.SystemInClosedException;

public class ClientRemoveGreater extends ClientCommand{
    private final ArgumentRequester argumentRequester;

    public ClientRemoveGreater(ArgumentRequester argumentRequester){
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
