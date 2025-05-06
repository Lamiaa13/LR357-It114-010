package M4.Part3;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    private int port = 3000;
    private ServerSocket serverSocket;
    private ConcurrentHashMap<String, ServerThread> clients = new ConcurrentHashMap<>();

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port: " + port);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ServerThread thread = new ServerThread(clientSocket, this);
            thread.start();
        }
    }

    public void registerClient(String id, ServerThread thread) {
        clients.put(id, thread);
        broadcast("Server", id + " has joined the chat.");
    }

    public void removeClient(String id) {
        clients.remove(id);
        broadcast("Server", id + " has left the chat.");
    }

    public void broadcast(String sender, String message) {
        String formatted = sender + ": " + message;
        for (ServerThread t : clients.values()) {
            t.sendMessage(formatted);
        }
    }

    public void relayPrivateMessage(String senderId, String targetId, String message) {
        ServerThread target = clients.get(targetId);
        if (target != null) {
            String msg = "PM from " + senderId + ": " + message;
            target.sendMessage(msg);
            ServerThread sender = clients.get(senderId);
            if (sender != null) sender.sendMessage(msg);
        } else {
            ServerThread sender = clients.get(senderId);
            if (sender != null) sender.sendMessage("User not found: " + targetId);
        }
    }

    public void broadcastShuffledMessage(String senderId, String message) {
        String shuffled = new StringBuilder(message).reverse().toString(); // simple shuffle for demo
        broadcast("Shuffled from " + senderId, shuffled);
    }

    public static void main(String[] args) throws IOException {
        int port = 3000;
        if (args.length > 0) port = Integer.parseInt(args[0]);
        new Server(port).start();
    }
}
