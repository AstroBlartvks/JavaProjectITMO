package org.AstrosLab.command.ClientCommand;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class CommandIdentifier {
    private HashMap<String, ClientCommand> commandList = new HashMap<String, ClientCommand>();

    public CommandIdentifier(){
        commandList.put("info", new ClientInfo());
    }

    public ClientCommand getCommand(String name){
        return commandList.get(name);
    }
}
