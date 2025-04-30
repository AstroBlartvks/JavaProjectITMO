package org.AstroLab.utils.ClientServer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerResponse {
    @JsonProperty("value")
    private Object value;
    @JsonProperty("status")
    private ResponseStatus status;

    @JsonCreator
    public ServerResponse(@JsonProperty("status") ResponseStatus status, @JsonProperty("value") Object value){
        this.status = status;
        this.value = value;
    }

    @Override
    public String toString(){
        return "\nResponse{\nstatus="+this.status.toString()+",\nvalue='''\n"+this.value+"\n'''}";
    }
}
