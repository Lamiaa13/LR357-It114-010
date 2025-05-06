package M4.Part3;

public class Constants {
    public static final String COMMAND_TRIGGER = "/";
}

// TextFX.java
// ucid: abc123 / 2025-05-06
package M4.Part3;

public class TextFX {
    public static String color(String message, String colorCode) {
        return colorCode + message + "\u001B[0m";
    }

    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
}