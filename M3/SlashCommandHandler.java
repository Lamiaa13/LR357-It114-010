package M3;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Challenge 2: Simple Slash Command Handler
-----------------------------------------
- Accept user input as slash commands
  - "/greet <name>" → Prints "Hello, <name>!"
  - "/roll <num>d<sides>" → Roll <num> dice with <sides> and returns a single outcome as "Rolled <num>d<sides> and got <result>!"
  - "/echo <message>" → Prints the message back
  - "/quit" → Exits the program
- Commands are case-insensitive
- Print an error for unrecognized commands
- Print errors for invalid command formats (when applicable)
- Capture 3 variations of each command except "/quit"
*/

public class SlashCommandHandler extends BaseClass {
    private static String ucid = "LR357"; // UCID updated to LR357
    // LR357 2/26/2025
    public static void main(String[] args) {
        printHeader(ucid, 2, "Objective: Build a basic slash command handler.");

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        while (true) {
            System.out.print("Type your command: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("/quit")) {
                System.out.println("Goodbye! See you later.");
                break;
            }

            if (input.toLowerCase().startsWith("/greet")) {
                String[] parts = input.split(" ", 2);
                if (parts.length > 1) {
                    System.out.println("Hi there, " + parts[1] + "!");
                } else {
                    System.out.println("Oops! Please provide your name.");
                }
                continue;
            }

            if (input.toLowerCase().startsWith("/echo")) {
                String[] parts = input.split(" ", 2);
                if (parts.length > 1) {
                    System.out.println(parts[1]);
                } else {
                    System.out.println("Oops! You need to provide a message.");
                }
                continue;
            }

            if (input.toLowerCase().startsWith("/roll")) {
                Pattern pattern = Pattern.compile("/roll (\\d+)d(\\d+)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(input);
                if (matcher.matches()) {
                    int num = Integer.parseInt(matcher.group(1));
                    int sides = Integer.parseInt(matcher.group(2));
                    if (num > 0 && sides > 0) {
                        int result = random.nextInt(num * sides) + 1;
                        System.out.println("Rolled " + num + "d" + sides + " and got " + result + " - Nice roll!");
                    } else {
                        System.out.println("Oops! The number of dice and sides must be positive.");
                    }
                } else {
                    System.out.println("Error: Invalid format. Please use /roll <num>d<sides>");
                }
                continue;
            }

            System.out.println("Unrecognized command. Please try again.");
        }

        printFooter(ucid, 2);
        scanner.close();
    }
}
