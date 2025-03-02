package org.AstrosLab.inputManager;

import org.AstrosLab.validate.DistanceMustBeHigherThanOne;
import org.AstrosLab.validate.IndexMustBeUnique;
import org.AstrosLab.validate.StringMustBeNotEmpty;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

public class ValidateRouteInput {
    protected Exception error;
    protected ArrayList<Integer> ids = new ArrayList<Integer>();

    public boolean isValidName(String name){
        if (isEmptyString(name)){
            this.error = new StringMustBeNotEmpty("The string must not be empty.");
            return false;
        }
        return true;
    }

    public boolean isValidDistance(double distance){
        if (distance <= 1){
            this.error = new DistanceMustBeHigherThanOne("The distance must be greater than 1");
            return false;
        }
        return true;
    }

    public boolean isValidId(int id){
        if (this.ids.contains(id) || id < 1){
            this.error = new IndexMustBeUnique("The index must be unique and greater than 1");
            return false;
        }
        this.ids.add(id);
        return true;
    }

    public boolean isValidDate(String data) {
        if (isEmptyString(data)) {
            this.error = new StringMustBeNotEmpty("The string must not be empty.");
            return false;
        }

        Instant newInstant;

        try {
            newInstant = Instant.parse(data);
        } catch (DateTimeParseException e) {
            this.error = e;
            return false;
        }

        try {
            Date newDate = Date.from(newInstant);
        } catch (IllegalArgumentException e){
            this.error = e;
            return false;
        }
        return true;
    }

    private boolean isEmptyString(String str){
        if (str == null || str.trim().isEmpty()) {
            return true;
        }

        return false;
    }

    public Exception getException(){
        return this.error;
    }

}
