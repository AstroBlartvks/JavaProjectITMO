package org.AstrosLab.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandArgument {
    private final Object value;
    private final Class<?> type; //Пока debug средство, потом уберу, мб

    public CommandArgument(Object value) {
        this.value = value;
        this.type = value != null ? value.getClass() : null;
    }

    @Override
    public String toString(){
        return this.value != null ? this.value.toString() : "null";
    }

}
