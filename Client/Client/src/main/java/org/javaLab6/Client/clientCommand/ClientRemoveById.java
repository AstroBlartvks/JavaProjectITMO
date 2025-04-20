package org.javaLab6.Client.clientCommand;

import org.javaLab6.Client.utils.command.CommandArgumentList;

public class ClientRemoveById extends ClientCommand {
    @Override
    public CommandArgumentList input(CommandArgumentList argumentList){
        argumentList.convertArgumentToNeedType(Integer::valueOf);
        return argumentList;
    }
}
