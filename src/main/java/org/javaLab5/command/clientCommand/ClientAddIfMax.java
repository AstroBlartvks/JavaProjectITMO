package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgument;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.inputManager.ArgumentRequester;

public class ClientAddIfMax extends ClientCommand{
    private final ArgumentRequester argumentRequester;

    public ClientAddIfMax(ArgumentRequester argumentRequester){
        this.argumentRequester = argumentRequester;
    }
    /**
     * Command 'Add'
     * @return CommandArgumentList arguments of command
     */
    @Override
    public CommandArgumentList input() {
        argumentList.addArgument(new CommandArgument(RouteDTOParser.parse(this.argumentRequester)));
        return argumentList;
    }
}
