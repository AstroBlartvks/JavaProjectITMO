package AstroLabServer.database;

import AstroLab.utils.model.CreateRouteDto;
import AstroLab.utils.model.Route;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class RouteDAO implements DatabaseAccessMethods<Route, CreateRouteDto, Route>{
    private static final Logger LOGGER = LogManager.getLogger(RouteDAO.class);
    private final Connection connection;
    private final LocationDAO locationDAO;
    private final CoordinatesDAO coordinatesDAO;

    public RouteDAO(Connection connection) {
        this.connection = connection;
        this.locationDAO = new LocationDAO(connection);
        this.coordinatesDAO = new CoordinatesDAO(connection);
    }

    @Override
    public void remove(Route route) throws SQLException {
        try (PreparedStatement ps = this.connection.prepareStatement(
                "DELETE FROM route WHERE id = ?")) {
            ps.setInt(1, route.getId());
            int res = ps.executeUpdate();
            System.out.println(res);
        }
        coordinatesDAO.remove(route.getCoordinates());
        locationDAO.remove(route.getFrom());
        locationDAO.remove(route.getTo());
        LOGGER.info("Route removed from DB and memory: {}", route.smallInfo());
    }

    @Override
    public Route create(CreateRouteDto route) throws SQLException {
        Integer coordinatesId = coordinatesDAO.create(route.getCoordinates());
        Integer fromLocationId = locationDAO.create(route.getFrom());
        Integer toLocationId = locationDAO.create(route.getTo());

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
            ps.setString(7, route.getOwnerLogin());

            ResultSet rs = ps.executeQuery();
            rs.next();

            Route newRoute = new Route();
            newRoute.setFromRouteDataTransferObject(route);
            newRoute.setId(rs.getInt("id"));
            newRoute.setCreationDate(data);
            LOGGER.info("Route added to DB and memory: {}", newRoute.smallInfo());
            return newRoute;
        }
    }

}