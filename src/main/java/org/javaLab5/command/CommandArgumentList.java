package org.javaLab5.command;

import lombok.Getter;

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
     * Retrieves all arguments except the command name itself.
     *
     * @return a new CommandArgumentList containing only element-related arguments
     */
    public CommandArgument getSecondArgument(){
        return argList.get(2);
    }

    /**
     * Returns the number of arguments in the list.
     *
     * @return the number of arguments
     */
    public int length(){
        return argList.size();
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
     * @param clazz the expected type of the first argument
     * @param <T> the type parameter
     * @throws IllegalArgumentException if the argument cannot be converted to the specified type
     */
    public <T> void convertArgumentType(Class<T> clazz) throws IllegalArgumentException {
        String argument = getFirstArgument().getValue().toString();

        try {
            Object parsedValue;

            if (clazz == Integer.class) {
                parsedValue = Integer.parseInt(argument);
            } else if (clazz == Double.class) {
                parsedValue = Double.parseDouble(argument);
            } else if (clazz == Boolean.class) {
                parsedValue = Boolean.parseBoolean(argument);
            } else if (clazz == String.class) {
                return;
            } else {
                throw new IllegalArgumentException("Unsupported type of argument: " + clazz.getSimpleName());
            }

            T arg = clazz.cast(parsedValue);
            updateByIndex(1, new CommandArgument(arg));
        } catch (Exception e) {
            throw new IllegalArgumentException("Argument must be capable of being converted to '" + clazz.getSimpleName() + "', but '" + argument + "' cannot be transformed", e);
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
