package AstroLabServer.database;

import AstroLab.utils.model.Coordinates;
import AstroLab.utils.model.CreateRouteDto;
import AstroLab.utils.model.Location;
import AstroLab.utils.model.Route;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class RouteDAO {
    public static final Logger LOGGER = LogManager.getLogger(RouteDAO.class);
    private final Connection connection;

    public RouteDAO(Connection connection) {
        this.connection = connection;
    }

    public Route create(CreateRouteDto route, String ownerLogin) throws SQLException{
        int coordinatesId = insertCoordinates(route.getCoordinates());
        int fromLocationId = insertLocation(route.getFrom());
        int toLocationId = insertLocation(route.getTo());

        try (PreparedStatement ps = this.connection.prepareStatement(
                "INSERT INTO Route(name, coordinate, from_location_id, to_location_id, distance, creation_date, owner_login) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id")) {

            ps.setString(1, route.getName());
            ps.setInt(2, coordinatesId);
            ps.setInt(3, fromLocationId);
            ps.setInt(4, toLocationId);
            ps.setDouble(5, route.getDistance());
            ps.setDate(6, new Date((new java.util.Date()).getTime()));
            ps.setString(7, ownerLogin);

            ResultSet rs = ps.executeQuery();
            rs.next();

            Route newRoute = new Route();
            newRoute.setFromRouteDataTransferObject(route);
            newRoute.setId(rs.getInt("id"));
            this.connection.commit();
            return newRoute;
        }
    }

    private int insertCoordinates(Coordinates coordinates) throws SQLException{
        int idCoordinates = 0;
        if (coordinates == null){
            return idCoordinates;
        }

        try (PreparedStatement ps = this.connection.prepareStatement(
                "INSERT INTO Coordinates(x, y) VALUES (?, ?) RETURNING coordinates_id")) {
            ps.setDouble(1, coordinates.getX());
            ps.setDouble(2, coordinates.getY());
            ResultSet rs = ps.executeQuery();
            rs.next();
            idCoordinates = rs.getInt("coordinates_id");
        }
        return idCoordinates;
    }

    private int insertLocation(Location location) throws SQLException {
        int idLocation = 0;
        if (location == null){
            return idLocation;
        }

        try (PreparedStatement ps = this.connection.prepareStatement(
                "INSERT INTO Location(x, y, z, name) " +
                        "VALUES (?, ?, ?, ?) RETURNING location_id")) {
            ps.setLong(  1, location.getX());
            ps.setDouble(2, location.getY());
            ps.setDouble(3, location.getZ());
            ps.setString(4, location.getName());
            ResultSet rs = ps.executeQuery();
            rs.next();
            idLocation = rs.getInt("location_id");
        }
        return idLocation;
    }

}