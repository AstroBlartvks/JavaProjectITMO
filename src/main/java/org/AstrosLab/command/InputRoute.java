package org.AstrosLab.command;

import org.AstrosLab.inputManager.Asker;
import org.AstrosLab.inputManager.ValidateRouteInput;

import java.util.ArrayList;

public class InputRoute {
    public static ArrayList<String> input(String strCommandInLine) throws Exception {
        ValidateRouteInput validRouteInput = new ValidateRouteInput();
        ArrayList<String> response = new ArrayList<String>();
        response.add(strCommandInLine);

        Asker ask = new Asker();
        ask.setValidRouteInput(validRouteInput);

        String name = ask.askString("Write 'name'");
        if (ask.getException() != null || name == null){
            throw ask.getException();
        }
        response.add(name);

        String coordinates = ask.askCoordinates("Write 'X' -> Coordinates", "Write 'Y' -> Coordinates");
        if (ask.getException() != null || coordinates == null){
            throw ask.getException();
        }
        response.add(coordinates);

        String from = ask.askFromTo("Write 'X' -> From(Location) || press Enter (null -> From)", "Write 'Y' -> From(Location)", "Write 'Z' -> From(Location)", "Write 'Name' -> From(Location)");
        if (ask.getException() != null || from == null){
            throw ask.getException();
        }
        response.add(from);

        String to = ask.askFromTo("Write 'X' -> To(Location) || press Enter (null -> To)", "Write 'Y' -> To(Location)", "Write 'Z' -> To(Location)", "Write 'Name' -> To(Location)");
        if (ask.getException() != null || to == null){
            throw ask.getException();
        }
        response.add(to);

        String distance = ask.askDistance("Write 'distance'");
        if (ask.getException() != null || distance == null){
            throw ask.getException();
        }
        response.add(distance);

        return response;
    }
}
