package com.jobappnetwork.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import com.jobappnetwork.protocol.Protocol;

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
            while (!clientSocket.isClosed()) {
                // Read the command from the client
                String commandStr = in.readLine();
                // System.out.println("Debug - ClientHandler: Received command string: " +
                // commandStr);

                if (commandStr == null) {
                    // System.out.println("Debug - ClientHandler: Client disconnected (null
                    // command)");
                    break;
                }

                try {
                    int command = Integer.parseInt(commandStr);
                    // System.out.println("Debug - ClientHandler: Processing command: " +
                    // Protocol.getCommandName(command));
                    String response;

                    // Only try to read additional data for commands that require it
                    if (commandProcessor.requiresData(command)) {
                        // System.out.println("Debug - ClientHandler: Command requires data: " +
                        // Protocol.getCommandName(command));
                        String data = in.readLine();
                        // System.out.println("Debug - ClientHandler: Additional data: " + data);
                        if (data == null) {
                            // System.out.println("Debug - ClientHandler: Data is null, sending error
                            // response");
                            out.println("ERROR: Missing data for command " + Protocol.getCommandName(command));
                            out.println("END_RESPONSE");
                            out.flush();
                            continue;
                        }
                        response = commandProcessor.processCommandWithData(command, data);
                    } else {
                        // System.out.println("Debug - ClientHandler: Command doesn't require additional
                        // data");
                        response = commandProcessor.processCommand(command);
                    }

                    // Step 13: Send posting update to job seekers
                    // System.out.println("Debug - ClientHandler: Sending response: " + response);
                    // Send the response line by line
                    for (String line : response.split("\n")) {
                        out.println(line);
                    }
                    // Send end marker
                    out.println("END_RESPONSE");
                    out.flush(); // Ensure the response is sent immediately
                } catch (NumberFormatException e) {
                    // System.out.println("Debug - ClientHandler: Invalid command format: " +
                    // commandStr);
                    out.println("ERROR: Invalid command format");
                    out.println("END_RESPONSE");
                    out.flush();
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