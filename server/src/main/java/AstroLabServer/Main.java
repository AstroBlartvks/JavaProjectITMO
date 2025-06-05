package AstroLabServer;

import AstroLabServer.database.DatabaseHandler;

public class Main {
    public static void main(String[] args) {
        if (args.length < 6) {
            Server.LOGGER.fatal("Not enough parameters (It have to be 6: <port, dbHost, dbPort, dbName, dbUser, dbPassword>");
            return;
        }

        int serverPort;
        String databaseHost;
        int databasePort;
        String databaseName;
        String databaseUser;
        String databasePassword;

        try {
            serverPort = Integer.parseInt(args[0]);
            databaseHost = args[1];
            databasePort = Integer.parseInt(args[2]);
            databaseName = args[3];
            databaseUser = args[4];
            databasePassword = args[5];
        } catch (NumberFormatException e) {
            Server.LOGGER.fatal("Your port is broken {}", e.getMessage());
            return;
        }

        DatabaseHandler databaseHandler = new DatabaseHandler(
                databaseHost,
                databasePort,
                databaseName,
                databaseUser,
                databasePassword
        );

        Server server = new Server(serverPort, databaseHandler);
        server.run();
    }
}