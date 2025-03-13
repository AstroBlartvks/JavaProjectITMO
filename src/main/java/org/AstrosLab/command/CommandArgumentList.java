package org.AstrosLab.command;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommandArgumentList {
    private final List<CommandArgument> argList = new ArrayList<CommandArgument>();

    public void addArgument(CommandArgument arg){
        this.argList.add(arg);
    }

    public CommandArgumentList getElementArguments(){
        CommandArgumentList elementArgs = new CommandArgumentList();
        this.argList.subList(2, this.argList.size()).forEach(elementArgs::addArgument);
        return elementArgs;
    }

    public int length(){
        return argList.size();
    }

    public CommandArgument getCommand(){
        return getArgumentByIndex(0);
    }

    public CommandArgument getFirstArgument(){
        return getArgumentByIndex(1);
    }

    public CommandArgument getArgumentByIndex(int index){
        return argList.get(index);
    }

    public void updateByIndex(int index, CommandArgument arg){
        argList.set(index, arg);
    }

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

    private <T> T parseUsingReflection(Class<T> type, String argument) throws Exception {
        try {
            return (T)type.getMethod("valueOf", String.class).invoke(null, argument);
        } catch (NoSuchMethodException e) {
            throw new Exception("Unsupported type: " + type.getName());
        } catch  (Exception e){
            throw new Exception("Unexpected error: " + e);
        }
    }

    @Override
    public String toString(){
        return argList.toString();
    }
}
