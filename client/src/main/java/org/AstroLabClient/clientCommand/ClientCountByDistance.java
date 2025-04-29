package org.AstroLabClient.clientCommand;

import org.AstroLab.utils.command.CommandArgumentList;

public class ClientCountByDistance extends ClientCommand{
    @Override
    public CommandArgumentList input(CommandArgumentList argumentList){
        argumentList.convertArgumentToNeedType(Double::valueOf);
        return argumentList;
    }
}
