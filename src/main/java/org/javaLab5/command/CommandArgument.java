package org.javaLab5.command;

import lombok.Getter;

/**
 * Abstraction over arguments passed to a command
 */
@Getter
public class CommandArgument {
    private final Object value;
    /**
     * Constructor for creating the 'command-argument' class
     *
     * @param value object to send to a command
     */
    public CommandArgument(Object value) {
        this.value = value;
    }

    /**
     * Return еру text representation of the object
     *
     * @return String text representation
     */
    @Override
    public String toString() {
        return this.value != null ? this.value.toString() : "null";
    }

}
