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
    private static final int SERVER_PORT = 8080;

    public ClientInteraction() {
        try {
            // Connect to the server
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // System.out.println("Connected to server successfully.");
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
            // System.out.println("Debug - ClientInteraction: Sending command: " +
            // Protocol.getCommandName(command));
            out.println(command);
            out.flush();

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null && !line.equals("END_RESPONSE")) {
                response.append(line).append("\n");
            }

            String responseStr = response.toString();
            if (responseStr.startsWith("ERROR:")) {
                // System.out.println("Debug - ClientInteraction: Got error response from
                // server: " + responseStr);
                return responseStr;
            }

            // System.out.println("Debug - ClientInteraction: Got response from server: " +
            // (responseStr.length() > 0 ? "not null" : "null"));
            return responseStr;
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
            // System.out.println("Debug - ClientInteraction: Sending command with data: " +
            // Protocol.getCommandName(command));
            // System.out.println("Debug - ClientInteraction: Command value: " + command);
            // System.out.println("Debug - ClientInteraction: Data: " + data);

            out.println(command);
            out.flush();

            out.println(data);
            out.flush();

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null && !line.equals("END_RESPONSE")) {
                response.append(line).append("\n");
            }

            String responseStr = response.toString();
            if (responseStr.startsWith("ERROR:")) {
                // System.out.println("Debug - ClientInteraction: Got error response from
                // server: " + responseStr);
                return responseStr;
            }

            // System.out.println("Debug - ClientInteraction: Got response from server: " +
            // (responseStr.length() > 0 ? "not null" : "null"));
            return responseStr;
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
