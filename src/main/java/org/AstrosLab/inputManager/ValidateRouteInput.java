package org.AstrosLab.inputManager;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

public class ValidateRouteInput {
    protected ArrayList<Integer> ids = new ArrayList<Integer>();

    public boolean isValidName(String name) throws Exception{
        if (isEmptyString(name)){
            throw new Exception("The string must not be empty.");
        }
        return true;
    }

    public boolean isValidDistance(double distance) throws Exception{
        if (distance <= 1){
            throw new Exception("The distance must be greater than 1");
        }
        return true;
    }

    public boolean isValidId(int id) throws Exception{
        if (this.ids.contains(id) || id < 1){
            throw new Exception("The index must be unique and greater than 1");
        }
        this.ids.add(id);
        return true;
    }

    public boolean isValidDate(String data) throws Exception{
        if (isEmptyString(data)) {
            throw new Exception("The string must not be empty.");
        }

        Instant newInstant;

        try {
            newInstant = Instant.parse(data);
        } catch (DateTimeParseException e) {
            throw e;
        }

        try {
            Date newDate = Date.from(newInstant);
        } catch (IllegalArgumentException e){
            throw e;
        }
        return true;
    }

    private boolean isEmptyString(String str){
        if (str == null || str.trim().isEmpty()) {
            return true;
        }
        return false;
    }

}
