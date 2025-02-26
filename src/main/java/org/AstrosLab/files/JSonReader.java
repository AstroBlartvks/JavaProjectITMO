package org.AstrosLab.files;

import org.AstrosLab.collectrion.customCollection;
import org.AstrosLab.model.Coordinates;
import org.AstrosLab.model.Location;
import org.AstrosLab.model.Route;
import org.AstrosLab.validate.ValidateRoute;
import org.AstrosLab.validate.ValidateRouteJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class JSonReader extends ReadHandler {
    private final ValidateRouteJSON valdRoute = new ValidateRouteJSON();

    @Override
    public customCollection readFile(String Path){
        customCollection newCollection = new customCollection();
        JSONParser parser = new JSONParser();

        try (FileReader filereader = new FileReader(Path)) {
            JSONObject rootJsonObject;

            try {
                rootJsonObject = (JSONObject) parser.parse(filereader);
            } catch (IOException | ParseException e) {
                this.error = e;
                return null;
            }

            Object obj = rootJsonObject.get("RoutesNames");
            ArrayList<String> RoutesNames = getRouteTitles(obj);
            if (RoutesNames == null) {
                return null;
            }

            for (String name : RoutesNames){

                Route tempRoute = readRoutefromJSON(rootJsonObject, name);

                if (tempRoute == null){
                    return null;
                }

                newCollection.addElement(tempRoute);
            }

        } catch (IOException e){
            this.error = e;
            return null;
        }

        return newCollection;
    }

    @Override
    public Exception getException(){
        return this.error;
    }

    public ArrayList<String> getRouteTitles(Object obj){
        ArrayList<String> RoutesNames = new ArrayList<>();

        if (obj instanceof JSONArray jsonArray) {

            for (Object item : jsonArray) {
                if (item instanceof String) {
                    RoutesNames.add((String) item);
                } else {
                    this.error = new TitleElementIsNotStringException("The array element is not a string");
                    return null;
                }
            }
        } else {
            this.error = new RouteNamesIncorrectFormatException("RoutesNames match or contain an incorrect format");
            return null;
        }

        return RoutesNames;
    }

    public Route readRoutefromJSON(JSONObject rootJsonObject, String nameRoute){
        Object obj = rootJsonObject.get(nameRoute);
        String[] requiredFields = {"id", "name", "distance", "coordinates", "from", "to", "creationDate"};

        if (!(obj instanceof JSONObject jsonObjectRoute)) {
            this.error = new ObjectIsNotAJSONObjectException("The object is not a JSONObject (trying to parse route: "+nameRoute+"). Fix it");
            return null;
        }

        for (String field : requiredFields){
            if (!jsonObjectRoute.containsKey(field)){
                this.error = new ObjectNotContainAllFieldsException("The object \""+nameRoute+"\" does not contain minimum the necessary field: \'"+field+"\'\nFix it and try again.");
                return null;
            }
        }

        int id = this.valdRoute.getId(jsonObjectRoute);
        String name = this.valdRoute.getName(jsonObjectRoute);
        double distance = this.valdRoute.getDistance(jsonObjectRoute);
        java.util.Date creationDate = this.valdRoute.getCreationDate(jsonObjectRoute);

        Coordinates coords = this.valdRoute.getCoordinates(jsonObjectRoute);
        // coords сделать

        Location from = this.valdRoute.getfromLocation(jsonObjectRoute);
        Location to = this.valdRoute.gettoLocation(jsonObjectRoute);

        if (this.sendError()){
            return null;
        }

        Route newRoute = new Route();
        newRoute.setId(id);
        newRoute.setName(name);
        newRoute.setDistance(distance);
        newRoute.setCreationDate(creationDate);
        newRoute.setCoordinates(coords);
        newRoute.setFrom(from);
        newRoute.setTo(to);

        return newRoute;
    }

    private boolean sendError(){
        if (this.valdRoute.getException() != null){
            this.error = this.valdRoute.getException();
            return true;
        }
        return false;
    }
}
