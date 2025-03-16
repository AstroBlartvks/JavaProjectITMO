package org.javaLab5.command;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstraction over arguments passed to a command
 */
@Getter
@Setter
public class CommandArgument {
    private final Object value;
    private final Class<?> type; //Пока debug средство, потом уберу, мб

    /**
     * Constructor for creating the 'command-argument' class
     *
     * @param value object to send to a command
     */
    public CommandArgument(Object value) {
        this.value = value;
        this.type = value != null ? value.getClass() : null;
    }

    /**
     * Return еру text representation of the object
     *
     * @return String text representation
     */
    @Override
    public String toString(){
        return this.value != null ? this.value.toString() : "null";
    }

}
