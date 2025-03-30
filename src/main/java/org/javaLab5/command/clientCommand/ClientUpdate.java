package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.inputManager.ArgumentRequester;

public class ClientUpdate extends ClientCommand{
    private final ArgumentRequester argumentRequester;

    public ClientUpdate(ArgumentRequester argumentRequester){
        this.argumentRequester = argumentRequester;
    }

    @Override
    public CommandArgumentList input() throws IllegalArgumentException {

        if (argumentList.getFirstArgument() == null){
            throw new IllegalArgumentException("The 'update' command has syntax and must contain the 'id' argument example: 'update id {element}'");
        }

        argumentList.convertArgumentToNeedType(Integer::valueOf);
        argumentList.addArgument(new CommandArgument(RouteDTOParser.parse(this.argumentRequester)));

        return argumentList;
    }
}
