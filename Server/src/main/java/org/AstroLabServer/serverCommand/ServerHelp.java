package org.AstroLabServer.serverCommand;

import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;

public class ServerHelp extends ServerCommand{
    @Override
    public ServerResponse execute(CommandArgumentList args){
        String Text = """
                help:\s
                \toutput help for available commands
                info:\s
                \toutput information about the collection (type, initialization date, number of elements, etc.) to the standard output stream
                show:\s
                \toutput all elements of the collection in a string representation to the standard output stream
                add {element}:\s
                \tadd a new element to the collection
                update id {element}:\s
                \tupdate the value of the element a collection whose id is equal to the specified
                remove_by_id id:\s
                \tdelete an item from the collection by its id
                clear:\s
                \tclear the collection
                save:\s
                \tsave the collection to a file
                execute_script file_name:\s
                \tread and execute the script from the specified file. The script contains commands in the same form as they are entered by the user interactively.
                exit:\s
                \tterminate the program (without saving to a file)
                add_if_max {element}:\s
                \tadd a new element to the collection if its value exceeds the value of the largest element in this collection
                add_if_min {element}:\s
                \tadd a new element to the collection if its value is less than that of the smallest element in this collection
                remove_greater {element}:\s
                \tremove from the collection all elements exceeding the specified limit
                count_by_distance distance:\s
                \toutput the number of elements whose value of the distance field is equal to the specified one
                count_greater_than_distance distance:\s
                \toutput the number of elements whose value of the distance field is greater than the specified one
                print_field_descending_distance:\s
                \tprint the values of the distance field of all elements in descending order""";

        return new ServerResponse(ResponseStatus.TEXT, Text);
    }
}
