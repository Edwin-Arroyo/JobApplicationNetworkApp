package com.jobappnetwork;

import com.jobappnetwork.server.Server;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Main class for the Job Application Network.
 * This class starts the server on port 8080.
 */
public class Main {
    public static void main(String[] args) {
        int port = 8080; // Server port

        try {
            // Get local IP address
            InetAddress localHost = InetAddress.getLocalHost();
            String ipAddress = localHost.getHostAddress();

            System.out.println("==================================================");
            System.out.println("Starting Job Application Network Server...");
            System.out.println("Server IP: " + ipAddress);
            System.out.println("Server Port: " + port);
            System.out.println("Server is ready to accept connections");
            System.out.println("==================================================");

            Server server = new Server(port);
            server.start();
        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        }
    }
}
