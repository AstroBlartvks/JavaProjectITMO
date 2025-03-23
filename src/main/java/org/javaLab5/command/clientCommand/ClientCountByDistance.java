package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgumentList;

public class ClientCountByDistance extends ClientCommand{
    @Override
    public CommandArgumentList input(){
        argumentList.convertArgumentType(Double.class);
        return argumentList;
    }
}
