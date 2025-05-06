package M4.Part3;

import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter out;
    private BufferedReader in;
    private String clientId;

    public ServerThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            clientId = "Client" + socket.getPort();
            server.registerClient(clientId, this);

            String line;
            while ((line = in.readLine()) != null) {
                processCommand(line);
            }
        } catch (IOException e) {
            System.out.println("Connection lost with " + clientId);
        } finally {
            server.removeClient(clientId);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private void processCommand(String input) {
        if (input.startsWith("/pm ")) {
            String[] parts = input.split(" ", 3);
            if (parts.length >= 3) {
                server.relayPrivateMessage(clientId, parts[1], parts[2]);
            }
        } else if (input.startsWith("/shuffle ")) {
            String msg = input.substring(9);
            server.broadcastShuffledMessage(clientId, msg);
        } else {
            server.broadcast(clientId, input);
        }
    }
}
