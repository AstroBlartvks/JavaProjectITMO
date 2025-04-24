package org.javaLab6.Client.utils.command;

import lombok.Getter;

import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of command arguments used in command execution.
 */
@Getter
public class CommandArgumentList {
    private final List<CommandArgument> argList = new ArrayList<>();

    /**
     * Adds a new argument to the list.
     *
     * @param arg the argument to add
     */
    public void addArgument(CommandArgument arg){
        this.argList.add(arg);
    }

    /**
     * Retrieves the command argument (first element in the list).
     *
     * @return the command argument
     */
    public CommandArgument getCommand(){
        return getArgumentByIndex(0);
    }

    /**
     * Retrieves the first argument (excluding the command name itself).
     *
     * @return the first argument
     */
    public CommandArgument getFirstArgument(){
        return getArgumentByIndex(1);
    }

    /**
     * Retrieves an argument by its index.
     *
     * @param index the index of the argument
     * @return the argument at the specified index
     */
    public CommandArgument getArgumentByIndex(int index){
        return argList.get(index);
    }

    /**
     * Updates an argument at the specified index.
     *
     * @param index the index of the argument to update
     * @param arg the new argument value
     */
    public void updateByIndex(int index, CommandArgument arg){
        argList.set(index, arg);
    }

    /**
     * Validates and converts the first argument to the specified type.
     *
     * @param parser the expected type of the first argument
     * @param <T> the type parameter
     * @throws IllegalArgumentException if the argument cannot be converted to the specified type
     */
    public <T> void convertArgumentToNeedType(Function<String, T> parser) throws IllegalArgumentException {
        String argument = (String) getFirstArgument().getValue();

        try {
            T parsedValue = parser.apply(argument);
            updateByIndex(1, new CommandArgument(parsedValue));
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Argument must be convertible to '" + parser.getClass() + "', but '" + argument + "' cannot be transformed", e
            );
        }
    }

    /**
     * Returns a string representation of the argument list.
     *
     * @return a string representation of the argument list
     */
    @Override
    public String toString(){
        return argList.toString();
    }
}
