package org.javaLab6;

import org.javaLab6.Client.Client;

public class Main {
    public static void main(String[] args) {
        Client client = new Client("localhost", 8000);
        client.run();
    }
}