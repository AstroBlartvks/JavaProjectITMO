package org.javaLab6.Client.clientCommand;

import org.javaLab6.Client.utils.command.CommandArgumentList;

public class ClientCountByDistance extends ClientCommand{
    @Override
    public CommandArgumentList input(CommandArgumentList argumentList){
        argumentList.convertArgumentToNeedType(Double::valueOf);
        return argumentList;
    }
}
