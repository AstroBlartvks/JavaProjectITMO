package org.AstroLabClient.clientCommand;

import org.AstroLab.utils.command.CommandArgumentList;

public class ClientRemoveById extends ClientCommand {
    @Override
    public CommandArgumentList input(CommandArgumentList argumentList){
        argumentList.convertArgumentToNeedType(Integer::valueOf);
        return argumentList;
    }
}
