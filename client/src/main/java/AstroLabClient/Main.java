package AstroLabClient;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Not enough parameters (It have to be 2: <host port>");
            return;
        }

        String serverHost;
        int serverPort;

        try {
            serverHost = args[0];
            serverPort = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Your port is broken: " + e.getMessage());
            return;
        }

        Client client = new Client(serverHost, serverPort);
        client.run();
    }
}