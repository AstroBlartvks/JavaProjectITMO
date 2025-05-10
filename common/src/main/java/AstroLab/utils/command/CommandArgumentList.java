package AstroLab.utils.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Represents a list of command arguments used in command execution.
 */
public class CommandArgumentList {
    @JsonProperty("argList")
    private final List<CommandArgument> argList = new ArrayList<>();

    /**
     * Adds a new argument to the list.
     *
     * @param arg the argument to add
     */
    public void addArgument(CommandArgument arg) {
        this.argList.add(arg);
    }

    /**
     * Retrieves the command argument (first element in the list).
     *
     * @return the command argument
     */
    @JsonIgnore
    public CommandArgument getCommand() {
        return getArgumentByIndex(0);
    }

    /**
     * Retrieves the first argument (excluding the command name itself).
     *
     * @return the first argument
     */
    @JsonIgnore
    public CommandArgument getFirstArgument() {
        return argList.size() < 2 ? null : getArgumentByIndex(1);
    }

    /**
     * Retrieves an argument by its index.
     *
     * @param index the index of the argument
     * @return the argument at the specified index
     */
    @JsonIgnore
    public CommandArgument getArgumentByIndex(int index) {
        return argList.get(index);
    }

    /**
     * Returns a string representation of the argument list.
     *
     * @return a string representation of the argument list
     */
    @Override
    public String toString() {
        return argList.toString();
    }

    /**
     * Updates an argument at the specified index.
     *
     * @param index the index of the argument to update
     * @param arg the new argument value
     */
    public void updateByIndex(int index, CommandArgument arg) {
        argList.set(index, arg);
    }

    /**
     * Validates and converts the first argument to the specified type.
     *
     * @param parser the expected type of the first argument
     * @param <T> the type parameter
     * @throws IllegalArgumentException if the argument cannot be converted to the specified type
     */
    public <T> T convertArgumentToNeedType(Function<String, T> parser) throws IllegalArgumentException {
        String argument = (String) getFirstArgument().getValue();

        try {
            T parsedValue = parser.apply(argument);
            updateByIndex(1, new CommandArgument(parsedValue));
            return parsedValue;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Argument '" + argument + "' cannot be transformed", e
            );
        }
    }
}
