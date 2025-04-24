package org.javaLab6.utils.command;

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

    public void setArgList(List<CommandArgument> args){
        for (CommandArgument arg : args){
            addArgument(arg);
        }
    }

    /**
     * Retrieves argument with index 2
     *
     * @return a new CommandArgumentList containing only element-related arguments
     */
    public CommandArgument getLastArgument(){
        return argList.get(argList.size() - 1);
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
     * Returns a string representation of the argument list.
     *
     * @return a string representation of the argument list
     */
    @Override
    public String toString(){
        return argList.toString();
    }
}
