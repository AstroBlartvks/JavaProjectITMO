package AstroLabServer.database;

import AstroLab.utils.model.Coordinates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoordinatesDAO implements DatabaseAccessMethods<Integer, Coordinates, Coordinates>{
    private static final Logger LOGGER = LogManager.getLogger(CoordinatesDAO.class);
    private final Connection connection;

    public CoordinatesDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public Integer create(Coordinates input) throws SQLException {
        int idCoordinates;
        if (input == null){
            return null;
        }

        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Coordinates(x, y) VALUES (?, ?) RETURNING coordinates_id")) {
            ps.setDouble(1, input.getX());
            ps.setDouble(2, input.getY());
            ResultSet rs = ps.executeQuery();
            rs.next();
            idCoordinates = rs.getInt("coordinates_id");
            input.setId(idCoordinates);
        }
        LOGGER.info("Coordinates {} added to DB with id {}", input, idCoordinates);
        return idCoordinates;
    }

    @Override
    public void remove(Coordinates input) throws SQLException {
        if (input == null){
            return;
        }

        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM coordinates WHERE coordinates_id = ?")) {
            ps.setDouble(1, input.getId());
            ps.executeUpdate();
        }

        LOGGER.info("Coordinates {} removed from DB!", input);
    }
}
