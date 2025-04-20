package org.javaLab6.Client.clientCommand;

import org.javaLab6.Client.utils.command.CommandArgument;
import org.javaLab6.Client.utils.command.CommandArgumentList;
import org.javaLab6.Client.inputManager.ArgumentRequester;
import org.javaLab6.Client.inputManager.SystemInClosedException;

public class ClientUpdate extends ClientCommand{
    private final ArgumentRequester argumentRequester;

    public ClientUpdate(ArgumentRequester argumentRequester){
        this.argumentRequester = argumentRequester;
    }

    @Override
    public CommandArgumentList input(CommandArgumentList argumentList) throws IllegalArgumentException, SystemInClosedException {

        if (argumentList.getFirstArgument() == null){
            throw new IllegalArgumentException("The 'update' command has syntax and must contain the 'id' argument example: 'update id {element}'");
        }

        argumentList.convertArgumentToNeedType(Integer::valueOf);
        argumentList.addArgument(new CommandArgument(RouteDTOParser.parse(this.argumentRequester)));

        return argumentList;
    }
}
