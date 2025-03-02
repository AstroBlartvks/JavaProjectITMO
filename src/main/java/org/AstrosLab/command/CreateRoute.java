package org.AstrosLab.command;

import org.AstrosLab.model.Coordinates;
import org.AstrosLab.model.Location;
import org.AstrosLab.model.Route;

import java.util.ArrayList;

public class CreateRoute {
    public static Route create(int id, ArrayList<String> strCommandInLine) {
        Route newRoute = new Route();

        //get Name
        String name = strCommandInLine.get(1);

        //get Coordnates
        String strCoordinates = strCommandInLine.get(2);
        Coordinates coordinates = new Coordinates();
        Double CX = Double.valueOf(strCoordinates.split("\n")[0]);
        Double CY = Double.valueOf(strCoordinates.split("\n")[1]);
        coordinates.setX(CX);
        coordinates.setX(CY);

        //get Date
        java.util.Date creationDate = new java.util.Date();

        //get Location from
        Location from;
        String fromLocation = strCommandInLine.get(3);
        if (fromLocation != "null"){
            from = new Location();
            long fromLocationX = Long.parseLong(fromLocation.split("\n")[0]);
            Float fromLocationY = Float.valueOf(fromLocation.split("\n")[1]);
            Float fromLocationZ = Float.valueOf(fromLocation.split("\n")[2]);
            String fromLocationName = fromLocation.split("\n")[3];
            from.setX(fromLocationX);
            from.setY(fromLocationY);
            from.setZ(fromLocationZ);
            from.setName(fromLocationName);
        } else {
            from = null;
        }

        //get Location to
        Location to;
        String toLocation = strCommandInLine.get(4);
        if (toLocation != "null"){
            to = new Location();
            long toLocationX = Long.parseLong(toLocation.split("\n")[0]);
            Float toLocationY = Float.valueOf(toLocation.split("\n")[1]);
            Float toLocationZ = Float.valueOf(toLocation.split("\n")[2]);
            String toLocationName = toLocation.split("\n")[3];
            to.setX(toLocationX);
            to.setY(toLocationY);
            to.setZ(toLocationZ);
            to.setName(toLocationName);
        } else {
            to = null;
        }

        //get distance
        String strDistance = strCommandInLine.get(5);
        double distance = Double.parseDouble(strDistance);

        //set all arguments
        newRoute.setId(id);
        newRoute.setName(name);
        newRoute.setCoordinates(coordinates);
        newRoute.setCreationDate(creationDate);
        newRoute.setFrom(from);
        newRoute.setTo(to);
        newRoute.setDistance(distance);

        return newRoute;
    }

}
