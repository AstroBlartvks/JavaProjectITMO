package org.AstrosLab.validate;

import org.AstrosLab.files.ObjectIsNotAJSONObjectException;
import org.AstrosLab.model.Coordinates;
import org.AstrosLab.model.Location;
import org.json.simple.JSONObject;

import java.time.Instant;
import java.util.Date;

public class ValidateRouteJSON extends ValidateRoute{
    public int getId(JSONObject routeJsonObject){
        Object obj = routeJsonObject.get("id");
        if (!isType(obj, Integer.class)){
            return 0;
        }

        if (this.isValidId((int)obj)){
            return (int)obj;
        }
        return 0;
    }

    public String getName(JSONObject routeJsonObject){
        Object obj = routeJsonObject.get("name");
        if (!isType(obj, String.class)){
            return "";
        }

        if (this.isValidName((String)obj)){
            return (String)obj;
        }
        return "";

    }

    public double getDistance(JSONObject routeJsonObject){
        Object obj = routeJsonObject.get("distance");
        if (!isType(obj, double.class)){
            return 0.0;
        }

        if (this.isValidDistance((double)obj)){
            return (double)obj;
        }
        return 0;
    }

    public java.util.Date getCreationDate(JSONObject routeJsonObject){
        Object obj = routeJsonObject.get("creationDate");
        if (!isType(obj, String.class)){
            return null;
        }

        if (this.isValidDate((String)obj)){
            Instant newInstant = Instant.parse((String)obj);
            return Date.from(newInstant);
        }
        return null;
    }

    public Coordinates getCoordinates(JSONObject routeJsonObject){
        return null;
    }

    public Location getfromLocation(JSONObject routeJsonObject){
        Object obj = routeJsonObject.get("from");

        if (!(obj instanceof JSONObject)) {
            this.error = new ObjectIsNotAJSONObjectException("The object 'from' is not a JSONObject");
            return null;
        }
        return this.getLocation((JSONObject)obj);
    }

    public Location gettoLocation(JSONObject routeJsonObject){
        Object obj = routeJsonObject.get("to");

        if (!(obj instanceof JSONObject)) {
            this.error = new ObjectIsNotAJSONObjectException("The object 'to' is not a JSONObject");
            return null;
        }
        return this.getLocation((JSONObject)obj);
    }

    private Location getLocation(JSONObject jsonObject){
        Location location = new Location();
        String[] requiredFields = {"x", "y", "z", "name"};
        Class<?>[] allowedTypes = {Long.class, Float.class, Float.class, String.class};

        for (int i = 0; i < requiredFields.length; i++){
            if (!jsonObject.containsKey(requiredFields[i])){
                this.error = new LocationHaveNoFieldsException("Location doesn't have field: "+requiredFields[i]);
                return null;
            }

            Object obj = jsonObject.get(requiredFields[i]);
            if (!isType(obj, allowedTypes[i])){
                return null;
            }

            //Ужасный момент, я себе перехитрил
            if (i == 0){
                location.setX((long)obj);
            } else if (i == 1){
                location.setY((Float)obj);
            } else if (i == 2){
                location.setZ((Float)obj);
            } else if (i == 3){
                location.setName((String)obj);
            }
        }

        return location;
    }

    private <T> boolean isType(Object obj, Class<T> clazz) {
        boolean isType = false;
        return isType;
    }

}
