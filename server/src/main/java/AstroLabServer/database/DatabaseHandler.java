package AstroLabServer.database;

import AstroLab.utils.model.Coordinates;
import AstroLab.utils.model.Location;
import AstroLab.utils.model.Route;
import AstroLabServer.collection.CustomCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseHandler implements DatabaseReadable{
    public static final Logger LOGGER = LogManager.getLogger(DatabaseHandler.class);

    public Connection connect(String dbHost) throws IOException,
                                                     ClassNotFoundException,
                                                     SQLException {
        Class.forName("org.postgresql.Driver");
        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));
        String URL = "jdbc:postgresql://" + dbHost + ":5432/studs";
        Connection connection = DriverManager.getConnection(URL, info);
        connection.setAutoCommit(false);

        LOGGER.info("Connected to PostgreSQL successfully! {}", connection);
        return connection;
    }

    @Override
    public CustomCollection read(Connection connection) throws SQLException{
        CustomCollection collection = new CustomCollection();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM route")) {

            while (resultSet.next()) {
                Route route = new Route();
                route.setId(resultSet.getInt("id"));
                route.setName(resultSet.getString("name"));

                int coordinateId = resultSet.getInt("coordinate");

                if (coordinateId > 0){
                    try (PreparedStatement cordState = connection.prepareStatement("SELECT * FROM coordinates WHERE coordinates_id = ?")) {
                        cordState.setInt(1, coordinateId);
                        ResultSet rs = cordState.executeQuery();
                        rs.next();
                        Coordinates coordinates = new Coordinates();
                        coordinates.setX(rs.getDouble("x"));
                        coordinates.setY(rs.getDouble("y"));
                        route.setCoordinates(coordinates);
                    }
                } else {
                    route.setCoordinates(null);
                }

                route.setCreationDate(resultSet.getTimestamp("creation_date"));

                int fromLocationId = resultSet.getInt("from_location_id");

                if (fromLocationId > 0){
                    try (PreparedStatement cordState = connection.prepareStatement("SELECT * FROM location WHERE location_id = ?")) {
                        cordState.setInt(1, fromLocationId);
                        ResultSet rs = cordState.executeQuery();
                        rs.next();
                        Location locationFrom = new Location();
                        locationFrom.setX(rs.getInt("x"));
                        locationFrom.setY(rs.getFloat("y"));
                        locationFrom.setZ(rs.getFloat("z"));
                        locationFrom.setName(rs.getString("name"));
                        route.setFrom(locationFrom);
                    }
                } else {
                    route.setFrom(null);
                }

                int toLocationId = resultSet.getInt("to_location_id");

                if (toLocationId > 0){
                    try (PreparedStatement cordState = connection.prepareStatement("SELECT * FROM location WHERE location_id = ?")) {
                        cordState.setInt(1, toLocationId);
                        ResultSet rs = cordState.executeQuery();
                        rs.next();
                        Location locationTo = new Location();
                        locationTo.setX(rs.getInt("x"));
                        locationTo.setY(rs.getFloat("y"));
                        locationTo.setZ(rs.getFloat("z"));
                        locationTo.setName(rs.getString("name"));
                        route.setTo(locationTo);
                    }
                } else {
                    route.setTo(null);
                }

                route.setDistance(resultSet.getInt("distance"));
                route.setOwnerLogin(resultSet.getString("owner_login"));

                collection.addElement(route);
            }
        }
        return collection;
    }
}