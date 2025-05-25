package AstroLabServer.database;

import java.sql.SQLException;

public interface DatabaseAccessMethods<R, T, S> {
     R create(T input) throws SQLException;
     void remove(S input) throws SQLException;
}
