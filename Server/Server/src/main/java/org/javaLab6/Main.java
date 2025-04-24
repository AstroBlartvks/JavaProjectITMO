package org.javaLab6;

public class Main {
    public static void main(String[] args) {
        Server server = new Server("localhost", 8000);
        server.run();
    }
}