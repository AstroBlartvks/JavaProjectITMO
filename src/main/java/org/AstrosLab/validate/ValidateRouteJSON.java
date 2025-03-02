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

        if (!isType(obj, Long.class)){
            return 0;
        }

        if (this.isValidId(((Number)obj).intValue())){
            return ((Number)obj).intValue();
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
        if (!isType(obj, Double.class)){
            return 0.0;
        }

        if (this.isValidDistance(((Number)obj).doubleValue())){
            return ((Number)obj).doubleValue();
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
        Object obj = routeJsonObject.get("coordinates");
        if (obj == null){
            this.error = new ObjectIsNotAJSONObjectException("The object 'coordinates' can't be null!");
            return null;
        }

        if (!(obj instanceof JSONObject coorObject)) {
            this.error = new ObjectIsNotAJSONObjectException("The object is not a JSONObject. Fix it");
            return null;
        }

        Coordinates coord = new Coordinates();

        if (!(coorObject.containsKey("x") && coorObject.containsKey("y"))){
            this.error = new LocationHaveNoFieldsException("Coordinates doesn't have field: x or y");
            return null;
        }

        Object objX = coorObject.get("x");
        Object objY = coorObject.get("y");

        if (!isType(objX, Double.class)){
            return null;
        } else if (!isType(objY, Double.class)){
            return null;
        }

        if (objX == null){
            this.error = new TheFieldIsNullException("The field 'x' in 'coordinates' is NULL. Must be Double");
        } else if (objY == null){
            this.error = new TheFieldIsNullException("The field 'y' in 'coordinates' is NULL. Must be Double");
        }

        coord.setX((Double)objX);
        coord.setY((Double)objY);

        return coord;
    }

    public Location getfromLocation(JSONObject routeJsonObject){
        Object obj = routeJsonObject.get("from");
        if (obj == null){
            return null;
        }
        if (!(obj instanceof JSONObject)) {
            this.error = new ObjectIsNotAJSONObjectException("The object 'from' is not a JSONObject");
            return null;
        }
        return this.getLocation((JSONObject)obj);
    }

    public Location gettoLocation(JSONObject routeJsonObject){
        Object obj = routeJsonObject.get("to");
        if (obj == null){
            return null;
        }
        if (!(obj instanceof JSONObject)) {
            this.error = new ObjectIsNotAJSONObjectException("The object 'to' is not a JSONObject");
            return null;
        }
        return this.getLocation((JSONObject)obj);
    }

    private Location getLocation(JSONObject jsonObject){
        Location location = new Location();
        String[] requiredFields = {"x", "y", "z", "name"};
        Class<?>[] allowedTypes = {Long.class, Number.class, Number.class, String.class};

        for (int i = 0; i < 4; i++){

            if (!jsonObject.containsKey(requiredFields[i])){
                this.error = new LocationHaveNoFieldsException("Location doesn't have field: "+requiredFields[i]);
                return null;
            }

            Object obj = jsonObject.get(requiredFields[i]);

            if (!isType(obj, allowedTypes[i])){
                return null;
            }

            if (obj == null){
                this.error = new TheFieldIsNullException("The field "+requiredFields[i]+" is NULL");
                return null;
            }

            //Ужасный момент, я себя перехитрил
            if (i == 0){
                location.setX((long)obj);
            } else if (i == 1){
                location.setY(((Number)obj).floatValue());
            } else if (i == 2){
                location.setZ(((Number)obj).floatValue());
            } else if (i == 3){
                location.setName((String)obj);
            }
        }

        return location;
    }

    private <T> boolean isType(Object obj, Class<T> clazz) {
        boolean isType = clazz.isInstance(obj);

        if (!isType && obj != null){
            this.error = new IsNotSameObjectException("Objects have different types: "+obj.getClass()+ " and have to be "+clazz);;
        } else if (!isType){
            this.error = new IsNotSameObjectException("Object 1 is null, but have to be "+clazz);;
        }

        return isType;
    }

}
