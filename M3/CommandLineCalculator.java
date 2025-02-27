package M3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/*
Challenge 1: Command-Line Calculator
------------------------------------
- Accept two numbers and an operator as command-line arguments
- Supports addition (+) and subtraction (-)
- Allow integer and floating-point numbers
- Ensures correct decimal places in output based on input (e.g., 0.1 + 0.2 → 1 decimal place)
- Display an error for invalid inputs or unsupported operators
- Capture 5 variations of tests
*/

public class CommandLineCalculator extends BaseClass {
    private static String ucid = "LR357"; // UCID changed to LR357
    // LR357 2/26/2025
    public static void main(String[] args) {
        printHeader(ucid, 1, "Objective: Implement a command-line calculator.");

        if (args.length != 3) {
            System.out.println("Usage: java M3.CommandLineCalculator <number1> <operator> <number2>");
            printFooter(ucid, 1);
            return;
        }

        try {
            // Extract values from arguments
            BigDecimal number1 = new BigDecimal(args[0]);
            String operator = args[1];
            BigDecimal number2 = new BigDecimal(args[2]);
            BigDecimal result;

            // Perform the appropriate operation
            switch (operator) {
                case "+":
                    result = number1.add(number2);
                    break;
                case "-":
                    result = number1.subtract(number2);
                    break;
                default:
                    System.out.println("Error: Unsupported operator. Please use either + or -.");
                    printFooter(ucid, 1);
                    return;
            }

            // Determine the appropriate scale based on input decimal places
            int scale = Math.max(getDecimalPlaces(args[0]), getDecimalPlaces(args[2]));
            result = result.setScale(scale, RoundingMode.HALF_UP);
            
            // Displaying the result
            System.out.println("Calculation result: " + result);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Please ensure numbers are correctly formatted.");
        }

        printFooter(ucid, 1);
    }

    private static int getDecimalPlaces(String number) {
        if (number.contains(".")) {
            return number.length() - number.indexOf('.') - 1;
        }
        return 0;
    }
}
