package org.AstrosLab.files;

import org.AstrosLab.collectrion.customCollection;
import org.AstrosLab.model.Coordinates;
import org.AstrosLab.model.Location;
import org.AstrosLab.model.Route;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class JSonReader extends ReadHandler {
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

        if (!(obj instanceof JSONObject)) {
            this.error = new ObjectIsNotAJSONObjectException("The object is not a JSONObject (trying to parse route: "+nameRoute+"). Fix it");
            return null;
        }

        JSONObject jsonObjectRoute = (JSONObject) obj;

        if (!(jsonObjectRoute.containsKey("id") && jsonObjectRoute.containsKey("name") &&
                jsonObjectRoute.containsKey("distance") && jsonObjectRoute.containsKey("coordinates") &&
                jsonObjectRoute.containsKey("from") && jsonObjectRoute.containsKey("to") &&
                jsonObjectRoute.containsKey("creationDate"))) {

            this.error = new ObjectNotContainAllFieldsException("The object \""+nameRoute+"\" does not contain all the necessary fields");
            return null;
        }

        //Сделать наследующий клас от validateRoute -> ValidateRouteJson, чтобы там првоерять все JSON обхъекты

        Route newRoute = null;

        return newRoute;
    }

    private static Location parseLocation(JSONObject jsonObject) {
        Location location = new Location();
        location.setX((long)jsonObject.get("x"));
        location.setY((Float)jsonObject.get("y"));
        location.setZ((Float)jsonObject.get("z"));
        location.setName((String)jsonObject.get("name"));
        return location;
    }
    private static Coordinates parseCoordinates(JSONObject jsonObject) {
        Coordinates coord = new Coordinates();
        coord.setX((Double)jsonObject.get("x"));
        coord.setY((Double)jsonObject.get("y"));
        return coord;
    }
}
