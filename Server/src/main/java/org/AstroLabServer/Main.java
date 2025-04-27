package org.AstroLabServer;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(args);
        if (!server.isRunning()) return;
        server.run();
    }
}