package org.AstroLabClient.clientCommand;

import org.AstroLab.utils.command.CommandArgument;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLabClient.inputManager.ArgumentRequester;
import org.AstroLabClient.inputManager.SystemInClosedException;

public class ClientRemoveGreater extends ClientCommand{
    private final ArgumentRequester argumentRequester;

    public ClientRemoveGreater(ArgumentRequester argumentRequester){
        this.argumentRequester = argumentRequester;
    }
    /**
     * Command 'Add'
     * @return CommandArgumentList arguments of command
     */
    @Override
    public CommandArgumentList input(CommandArgumentList argumentList) throws IllegalArgumentException, SystemInClosedException {
        argumentList.addArgument(new CommandArgument(RouteDTOParser.parse(this.argumentRequester)));
        return argumentList;
    }
}
