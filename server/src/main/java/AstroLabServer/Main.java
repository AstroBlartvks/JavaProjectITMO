package AstroLabServer;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            Server.LOGGER.fatal("Not enough parameters (It have to be 1: <port>");
            return;
        }

        int serverPort;

        try {
            serverPort = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            Server.LOGGER.fatal("Your port is broken {}", e.getMessage());
            return;
        }

        Server server = new Server(serverPort);
        server.run();
    }
}