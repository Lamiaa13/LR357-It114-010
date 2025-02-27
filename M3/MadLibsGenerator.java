package M3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/*
Challenge 3: Mad Libs Generator (Randomized Stories)
-----------------------------------------------------
- Load a **random** story from the "stories" directory
- Read **each line** into a collection (i.e., ArrayList)
- Ask the user for a word to replace each placeholder (e.g., <adjective>) 
    - Any word the user provides is acceptable, no need to check if it matches the placeholder type
    - Any placeholder with underscores should be displayed with spaces instead
- Replace placeholders with the user's inputs (update the original line in collection)
*/

public class MadLibsGenerator extends BaseClass {
    private static final String STORIES_FOLDER = "M3/stories";
    private static String ucid = "LR357"; // UCID updated to LR357
    // LR357 2/26/2025
    public static void main(String[] args) {
        printHeader(ucid, 3, "Objective: Create a Mad Libs generator that fills in placeholders.");

        Scanner scanner = new Scanner(System.in);
        File folder = new File(STORIES_FOLDER);
        File[] files = folder.listFiles();
        
        if (files == null || files.length == 0) {
            System.out.println("Oops! No stories found in the 'stories' folder.");
            printFooter(ucid, 3);
            scanner.close();
            return;
        }

        // Choose a random story file
        File storyFile = files[new Random().nextInt(files.length)];
        List<String> lines = new ArrayList<>();
        
        try {
            lines = Files.readAllLines(storyFile.toPath());
        } catch (IOException e) {
            System.out.println("Error: Unable to read the story file.");
            printFooter(ucid, 3);
            scanner.close();
            return;
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            while (line.contains("<") && line.contains(">")) {
                int start = line.indexOf("<");
                int end = line.indexOf(">", start);
                if (end == -1) break;
                
                String placeholder = line.substring(start, end + 1); // Extract placeholder (e.g., "<adjective>")
                System.out.print("Please provide a word for " + placeholder.replace("_", " ") + ": ");
                String userInput = scanner.nextLine(); // Get user input
                
                line = line.replace(placeholder, userInput); // Replace all instances of placeholder with input
            }
            lines.set(i, line); // Update the modified line in the list
        }

        System.out.println("\nHere is your completed Mad Libs story:\n");
        for (String line : lines) {
            System.out.println(line);
        }

        printFooter(ucid, 3);
        scanner.close();
    }
}
