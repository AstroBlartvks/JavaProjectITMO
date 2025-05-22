package AstroLabServer;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            Server.LOGGER.fatal("Not enough parameters (It have to be 3: <host port db-host>");
            return;
        }

        String serverHost;
        int serverPort;
        String databaseHost;

        try {
            serverHost = args[0];
            serverPort = Integer.parseInt(args[1]);
            databaseHost = args[2];
        } catch (NumberFormatException e) {
            Server.LOGGER.fatal("Your host/port is broken {}", e.getMessage());
            return;
        }

        Server server = new Server(serverHost, serverPort, databaseHost);
        server.run();
    }
}