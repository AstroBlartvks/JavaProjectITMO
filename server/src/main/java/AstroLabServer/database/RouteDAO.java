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

    public void remove(Route route) throws SQLException {
        try (PreparedStatement ps = this.connection.prepareStatement(
                "DELETE FROM route WHERE id = ?")) {
            ps.setInt(1, route.getId());
            int res = ps.executeUpdate();
            System.out.println(res);
        }
        removeCoordinates(route.getCoordinates());
        removeLocation(route.getFrom());
        removeLocation(route.getTo());
    }

    private void removeCoordinates(Coordinates coordinates) throws SQLException {
        if (coordinates == null){
            return;
        }

        try (PreparedStatement ps = this.connection.prepareStatement(
                "DELETE FROM coordinates WHERE coordinates_id = ?")) {
            ps.setDouble(1, coordinates.getId());
            ps.executeUpdate();
        }
    }

    private void removeLocation(Location location) throws SQLException {
        if (location == null){
            return;
        }

        try (PreparedStatement ps = this.connection.prepareStatement(
                "DELETE FROM location WHERE location_id = ?")) {
            ps.setLong(1, location.getId());
            ps.executeUpdate();
        }
    }

    public Route create(CreateRouteDto route, String ownerLogin) throws SQLException {
        Integer coordinatesId = insertCoordinates(route.getCoordinates());
        Integer fromLocationId = insertLocation(route.getFrom());
        Integer toLocationId = insertLocation(route.getTo());

        try (PreparedStatement ps = this.connection.prepareStatement(
                "INSERT INTO Route(name, coordinate, from_location_id, to_location_id, distance, creation_date, owner_login) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id")) {

            Date data = new Date((new java.util.Date()).getTime());
            ps.setString(1, route.getName());
            if (coordinatesId != null) { ps.setInt(2, coordinatesId); }
            else { ps.setNull(2, Types.NULL); }
            if (fromLocationId != null) { ps.setInt(3, fromLocationId); }
            else { ps.setNull(3, Types.NULL); }
            if (toLocationId != null) { ps.setInt(4, toLocationId); }
            else { ps.setNull(4, Types.NULL); }
            ps.setDouble(5, route.getDistance());
            ps.setDate(6, data);
            ps.setString(7, ownerLogin);

            ResultSet rs = ps.executeQuery();
            rs.next();

            Route newRoute = new Route();
            newRoute.setFromRouteDataTransferObject(route);
            newRoute.setId(rs.getInt("id"));
            newRoute.setCreationDate(data);
            newRoute.setOwnerLogin(ownerLogin);
            return newRoute;
        }
    }

    private Integer insertCoordinates(Coordinates coordinates) throws SQLException {
        int idCoordinates = 0;
        if (coordinates == null){
            return null;
        }

        try (PreparedStatement ps = this.connection.prepareStatement(
                "INSERT INTO Coordinates(x, y) VALUES (?, ?) RETURNING coordinates_id")) {
            ps.setDouble(1, coordinates.getX());
            ps.setDouble(2, coordinates.getY());
            ResultSet rs = ps.executeQuery();
            rs.next();
            idCoordinates = rs.getInt("coordinates_id");
            coordinates.setId(idCoordinates);
        }
        return idCoordinates;
    }

    private Integer insertLocation(Location location) throws SQLException {
        int idLocation = 0;
        if (location == null){
            return null;
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
            location.setId(idLocation);
        }
        return idLocation;
    }

}