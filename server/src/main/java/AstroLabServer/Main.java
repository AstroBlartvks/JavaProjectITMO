package AstroLabServer;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            Server.LOGGER.fatal("Not enough parameters (It have to be 2: <host port>");
            return;
        }

        String serverHost;
        int serverPort;

        try {
            serverHost = args[0];
            serverPort = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            Server.LOGGER.fatal("Your host/port is broken {}", e.getMessage());
            return;
        }

        Server server = new Server(serverHost, serverPort);
        server.run();
    }
}