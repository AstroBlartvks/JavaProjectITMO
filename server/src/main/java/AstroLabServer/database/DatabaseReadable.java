package AstroLabServer.database;

import java.sql.Connection;

public interface DatabaseReadable<T> {
    T read(Connection connection) throws Exception;
}
