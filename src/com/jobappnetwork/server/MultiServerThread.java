package com.jobappnetwork.server;

// multi server thread - will be used to handle multiple clients on the server at once, using the same resource 
import java.net.*;
import java.io.*;

public class MultiServerThread extends Thread {
    private Socket socket = null;
    private String clientName;

    public MultiServerThread(Socket socket) {
        super("MultiServerThread");
        this.socket = socket;
    }

    public void run() {
        try (
                // Create input and output streams for the socket
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in))) {
            // Initial prompt for the client

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
