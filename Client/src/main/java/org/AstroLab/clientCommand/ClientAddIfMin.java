package org.AstroLab.clientCommand;

import org.AstroLab.utils.command.CommandArgument;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLab.inputManager.ArgumentRequester;
import org.AstroLab.inputManager.SystemInClosedException;

public class ClientAddIfMin extends ClientCommand {
    private final ArgumentRequester argumentRequester;

    public ClientAddIfMin(ArgumentRequester argumentRequester){
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
