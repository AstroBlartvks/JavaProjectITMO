package org.AstroLab.clientCommand;

import org.AstroLab.utils.command.CommandArgument;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLab.inputManager.ArgumentRequester;
import org.AstroLab.inputManager.SystemInClosedException;

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
