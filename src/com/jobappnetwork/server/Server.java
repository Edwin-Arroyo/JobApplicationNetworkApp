package com.jobappnetwork.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The main server class for the job application network.
 * This class listens for client connections and creates a new thread
 * for each client to handle their requests.
 */
public class Server {
    private final int port;
    private final CommandProcessor commandProcessor;
    private ServerSocket serverSocket;
    private boolean running;

    /**
     * Creates a new Server instance.
     * 
     * @param port The port to listen on
     */
    public Server(int port) {
        this.port = port;
        DataManager dataManager = new DataManager();
        this.commandProcessor = new CommandProcessor(dataManager);
        this.running = false;
    }

    /**
     * Starts the server and begins accepting client connections.
     * 
     * @throws IOException If there's an error starting the server
     */
    public void start() throws IOException {
        // Step 1: Listen on a specific port for a connection request
        serverSocket = new ServerSocket(port);
        running = true;

        System.out.println("Server started on port " + port);

        while (running) {
            try {
                // Step 3: Accept connection request
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Create a new thread to handle the client
                ClientHandler handler = new ClientHandler(clientSocket, commandProcessor);
                Thread clientThread = new Thread(handler);
                clientThread.start();
            } catch (IOException e) {
                if (running) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Stops the server and closes all resources.
     */
    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server socket: " + e.getMessage());
        }
    }

    /**
     * The main method to start the server.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        int port = 5000; // Default port

        Server server = new Server(port);
        try {
            server.start();
        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        }
    }
}
