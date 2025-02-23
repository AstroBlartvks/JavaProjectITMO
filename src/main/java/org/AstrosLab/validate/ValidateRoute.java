package org.AstrosLab.validate;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.time.Instant;
import java.util.Date;

public class ValidateRoute{
    Exception lastError;
    ArrayList<Integer> ids = new ArrayList<Integer>();

    public boolean isValidName(String name){
        if (isEmptyString(name)){
            this.lastError = new StringMustBeNotEmpty("The string must not be empty.");
            return false;
        }
        return true;
    }

    public boolean isValidCoordinates(double x, double y){

        try {
            Double newX = x;
            Double newY = y;
        } catch (Exception exp){
            this.lastError = exp;
            return false;
        }

        return true;
    }

    public boolean isValidLocation(long x, float y, float z, String name){
        if (isEmptyString(name)){
            this.lastError = new StringMustBeNotEmpty("The string must not be empty.");
            return false;
        }

        try {
            Float newY = y;
            Float newZ = z;
        } catch (Exception exp){
            this.lastError = exp;
            return false;
        }

        return true;
    }

    public boolean isValidDistance(double distance){
        if (distance <= 1){
            this.lastError = new DistanceMustBeHigherThanOne("The distance must be greater than 1");
            return false;
        }
        return true;
    }

    public boolean isValidId(int id){
        if (this.ids.contains(id) || id < 1){
            this.lastError = new IndexMustBeUnique("The index must be unique and greater than 1");
            return false;
        }
        this.ids.add(id);
        return true;
    }

    public boolean isValidDate(String data) {
        if (isEmptyString(data)) {
            this.lastError = new StringMustBeNotEmpty("The string must not be empty.");
            return false;
        }

        Instant newInstant;

        try {
            newInstant = Instant.parse(data);
        } catch (DateTimeParseException e) {
            this.lastError = e;
            return false;
        }

        try {
            Date newDate = Date.from(newInstant);
        } catch (IllegalArgumentException e){
            this.lastError = e;
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

}
