package M4.Part3;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        CompletableFuture.runAsync(() -> {
            while (true) {
                String line = scanner.nextLine();
                processClientCommands(line);
            }
        });
    }

    public void processClientCommands(String input) {
        if (input.startsWith("/connect ")) {
            String[] parts = input.split(":");
            try {
                socket = new Socket(parts[0].substring(9), Integer.parseInt(parts[1]));
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                CompletableFuture.runAsync(this::readMessages);
                System.out.println("Connected to server");
            } catch (IOException e) {
                System.out.println("Failed to connect.");
            }
        } else if (input.equals("/quit")) {
            System.exit(0);
        } else {
            if (out != null) out.println(input);
        }
    }

    public void readMessages() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Disconnected.");
        }
    }

    public static void main(String[] args) {
        new Client().start();
    }
}
