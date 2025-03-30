package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgumentList;

public class ClientRemoveById extends ClientCommand {
    @Override
    public CommandArgumentList input(){
        argumentList.convertArgumentToNeedType(Integer::valueOf);
        return argumentList;
    }
}
