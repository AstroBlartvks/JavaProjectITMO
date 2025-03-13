package org.AstrosLab.command.ServerCommand;

import org.AstrosLab.command.CommandArgumentList;

public class ServerHelp extends ServerCommand{
    @Override
    public ServerResponse execute(CommandArgumentList args) throws Exception {
        String Text = "help: \n\toutput help for available commands\n" +
                "info: \n\toutput information about the collection (type, initialization date, number of elements, etc.) to the standard output stream\n" +
                "show: \n\toutput all elements of the collection in a string representation to the standard output stream\n"+
                "add {element}: \n\tadd a new element to the collection\n" +
                "update id {element}: \n\tupdate the value of the element a collection whose id is equal to the specified\n" +
                "remove_by_id id: \n\tdelete an item from the collection by its id\n" +
                "clear: \n\tclear the collection\n" +
                "save: \n\tsave the collection to a file\n" +
                "execute_script file_name: \n\tread and execute the script from the specified file. The script contains commands in the same form as they are entered by the user interactively.\n" +
                "exit: \n\tterminate the program (without saving to a file)\n" +
                "add_if_max {element}: \n\tadd a new element to the collection if its value exceeds the value of the largest element in this collection\n" +
                "add_if_min {element}: \n\tadd a new element to the collection if its value is less than that of the smallest element in this collection\n" +
                "remove_greater {element}: \n\tremove from the collection all elements exceeding the specified limit\n" +
                "count_by_distance distance: \n\toutput the number of elements whose value of the distance field is equal to the specified one\n" +
                "count_greater_than_distance distance: \n\toutput the number of elements whose value of the distance field is greater than the specified one\n" +
                "print_field_descending_distance: \n\tprint the values of the distance field of all elements in descending order";

        return new ServerResponse(ResponseStatus.TEXT, Text);
    }
}
