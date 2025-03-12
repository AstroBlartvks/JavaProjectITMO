package org.AstrosLab.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandArgument {
    private final Object value;

    public CommandArgument(Object value) {
        this.value = value;
    }

}
