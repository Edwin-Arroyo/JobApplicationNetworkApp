package com.jobappnetwork.client;

import java.io.*;
import java.net.Socket;

/**
 * Handles the client-side network communication with the server.
 * This class is responsible for sending commands to the server and
 * receiving responses.
 */
public class ClientInteraction {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5000;

    public ClientInteraction() {
        try {
            // Connect to the server
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to server successfully.");
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }

    /**
     * Sends a command to the server
     * 
     * @param command The command code to send
     * @return The server's response
     */
    public String sendCommand(int command) {
        try {
            // Send the command to the server
            out.println(command);

            // Read the server's response
            String response = in.readLine();
            return response;
        } catch (IOException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * Sends a command with additional data to the server
     * 
     * @param command The command code to send
     * @param data    Additional data to send with the command
     * @return The server's response
     */
    public String sendCommandWithData(int command, String data) {
        try {
            // Send the command to the server
            out.println(command);

            // Send the additional data
            out.println(data);

            // Read the server's response
            String response = in.readLine();
            return response;
        } catch (IOException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * Closes the connection to the server
     */
    public void close() {
        try {
            if (out != null)
                out.close();
            if (in != null)
                in.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
