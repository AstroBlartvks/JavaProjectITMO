package org.AstrosLab.command.ServerCommand;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerResponse {
    private Object value;
    private ResponseStatus status;

    public ServerResponse(ResponseStatus status, Object value){
        this.status = status;
        this.value = value;
    }

    @Override
    public String toString(){
        return "Response{\nstatus="+this.status.toString()+",\nvalue='''\n"+this.value+"\n'''}";
    }
}
