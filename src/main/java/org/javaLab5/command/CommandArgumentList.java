package org.javaLab5.command;

import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    public CommandArgumentList getElementArguments(){
        CommandArgumentList elementArgs = new CommandArgumentList();
        this.argList.subList(2, this.argList.size()).forEach(elementArgs::addArgument);
        return elementArgs;
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
     * @param type the expected type of the first argument
     * @param <T> the type parameter
     * @throws Exception if the argument cannot be converted to the specified type
     */
    public <T> void checkArgumentType(Class<T> type) throws Exception {
        String argument = getFirstArgument().getValue().toString();
        Object convertedValue;

        try {
            if (type == String.class) {
                convertedValue = argument;
            } else if (type == Boolean.class) {
                convertedValue = Boolean.parseBoolean(argument);
            } else {
                convertedValue = parseUsingReflection(type, argument);
            }
            updateByIndex(1, new CommandArgument(convertedValue));

        } catch (Exception e) {
            throw new Exception("Argument must be capable of being converted to '" + type.getSimpleName() + "', but '" + argument + "' cannot be transformed", e);
        }
    }

    /**
     * Uses reflection to parse a string into the specified type.
     *
     * @param type the expected type
     * @param argument the string value to convert
     * @param <T> the type parameter
     * @return the converted value
     */
    private <T> T parseUsingReflection(Class<T> type, String argument){
        try {
            Method method = type.getMethod("valueOf", String.class);
            return type.cast(method.invoke(null, argument));
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Unsupported type: " + type.getName(), e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException("Error invoking valueOf on type: " + type.getName(), e.getCause());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot access valueOf method on type: " + type.getName(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while parsing", e);
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
