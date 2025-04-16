package com.jobappnetwork.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handles communication with a single client.
 * This class is responsible for reading commands from the client,
 * passing them to the command processor, and sending responses back.
 */
public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final CommandProcessor commandProcessor;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Creates a new ClientHandler for the given client socket.
     * 
     * @param socket           The client's socket connection
     * @param commandProcessor The processor for handling commands
     */
    public ClientHandler(Socket socket, CommandProcessor commandProcessor) {
        this.clientSocket = socket;
        this.commandProcessor = commandProcessor;
    }

    /**
     * Handles the client connection.
     * This method is called by the thread when started.
     */
    @Override
    public void run() {
        try {
            // Set up input and output streams
            in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Process commands until the client disconnects
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // Parse the command
                int command = Integer.parseInt(inputLine);

                // Process the command and get the response
                String response = commandProcessor.processCommand(command);

                // Send the response back to the client
                out.println(response);

                // If the command requires additional data, read and process it
                if (commandProcessor.requiresData(command)) {
                    String data = in.readLine();
                    if (data != null) {
                        response = commandProcessor.processCommandWithData(command, data);
                        out.println(response);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            // Clean up resources
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
                if (clientSocket != null)
                    clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}