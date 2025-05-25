package AstroLabServer.database;

import AstroLab.utils.model.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationDAO implements DatabaseAccessMethods<Integer, Location, Location> {
    private static final Logger LOGGER = LogManager.getLogger(LocationDAO.class);
    private final Connection connection;

    public LocationDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public Integer create(Location input) throws SQLException {
        int idLocation;
        if (input == null){
            return null;
        }

        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Location(x, y, z, name) " +
                        "VALUES (?, ?, ?, ?) RETURNING location_id")) {
            ps.setLong(  1, input.getX());
            ps.setDouble(2, input.getY());
            ps.setDouble(3, input.getZ());
            ps.setString(4, input.getName());
            ResultSet rs = ps.executeQuery();
            rs.next();
            idLocation = rs.getInt("location_id");
            input.setId(idLocation);
        }
        LOGGER.info("Location {} added to DB with id {}", input, idLocation);
        return idLocation;
    }

    @Override
    public void remove(Location input) throws SQLException {
        if (input == null){
            return;
        }

        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM location WHERE location_id = ?")) {
            ps.setLong(1, input.getId());
            ps.executeUpdate();
        }
        LOGGER.info("Location {} removed from DB!", input);
    }
}
