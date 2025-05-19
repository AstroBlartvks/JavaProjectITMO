package AstroLabServer.database;

import AstroLabServer.collection.CustomCollection;
import java.sql.Connection;

public interface DatabaseReadable {
    CustomCollection read(Connection connection) throws Exception;
}
