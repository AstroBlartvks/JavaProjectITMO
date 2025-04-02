package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgumentList;

public class ClientCountGreaterThanDistance extends ClientCommand{
    @Override
    public CommandArgumentList input(CommandArgumentList argumentList){
        argumentList.convertArgumentToNeedType(Double::valueOf);
        return argumentList;
    }
}
