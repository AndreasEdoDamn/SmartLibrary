package com.mycompany.smartlibrary;
import java.util.Scanner;

public class ConsoleHelper {

    //===========================================================================================================================//

    private final Scanner scanner;

    public ConsoleHelper() {
        scanner = new Scanner(System.in);
    }

    //===========================================================================================================================//

    public static void printLogo() {
        System.out.println("""
             _____                       _   _      _ _                         \s
            /  ___|                     | | | |    (_) |                         \s
            \\ `--. _ __ ___   __ _ _ __| |_| |     _| |__  _ __ __ _ _ __ _    _\s
             `--. \\ '_ ` _ \\ / _` | '__| __| |    | | '_ \\| '__/ _` | '__| | | |
            /\\__/ / | | | | | (_| | |  | |_| |___| | |_) | | | (_| | |  | |_| |
            \\____/|_| |_| |_|\\__,_|_|   \\__\\_____/_|_.__/|_|  \\__,_|_|   \\__, |
                                                                          __/ |
                                                                         |___/\s
           \s""");
    }

    public static void printOptions() {
        System.out.println("1. Show all LibraryResource");
        System.out.println("2. Add new LibraryResource");
        System.out.println("3. Update LibraryResource Stock");
        System.out.println("4. Borrow Item");
        System.out.println("5. Return Item\n");
        System.out.println("6. Add Member");
        System.out.println("7. Remove Member");
        System.out.println("8. View Members");
        System.out.println("9. Exit");
    }

    //===========================================================================================================================//

    public static void printNotification(String message) {
        int totalWidth = 72;

        int paddingLeft = (totalWidth - message.length()) / 2;
        int paddingRight = totalWidth - message.length() - paddingLeft;

        String leftSpace = " ".repeat(paddingLeft);
        String rightSpace = " ".repeat(paddingRight);

        String line = "─".repeat(totalWidth);

        System.out.println("┌" + line + "┐");
        System.out.println("│" + leftSpace + message + rightSpace + "│");
        System.out.println("└" + line + "┘");
    }

    public static void printOneLineNotification(String message) {
        int totalWidth = 72;

        int paddingLeft = (totalWidth - message.length()) / 2;
        int paddingRight = totalWidth - message.length() - paddingLeft;

        String leftSpace = "─".repeat(paddingLeft - 1);
        String rightSpace = "─".repeat(paddingRight - 1);

        System.out.println("─" + leftSpace + " " + message + " " + rightSpace + "─");
    }

    //===========================================================================================================================//

    public static void printError(String message) {
        System.out.println("\n[!] ERROR: " + message);
    }

    public static void printSuccess(String message) {
        System.out.println("\n[V] SUKSES: " + message);
    }

    public void pressEnterToContinue() {
        System.out.print("\nPress Enter to Continue...");
        scanner.nextLine();
    }

    public static void clearScreen() {
        System.out.print("\n".repeat(50));
    }

    //===========================================================================================================================//

    public int askForInt(String prompt, int min, int max) {
        while (true) {
            System.out.printf("%s (%d - %d): ", prompt, min, max);
            String input = scanner.nextLine();

            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Error: Input must be between " + min + " and " + max + "!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Input must be an Integer!");
            }
        }
    }

    public int askForIntBiggerThan(String prompt, int min) {
        while (true) {
            System.out.printf("%s (>= %d): ", prompt, min);
            String input = scanner.nextLine();

            try {
                int value = Integer.parseInt(input);
                if (value >= min) {
                    return value;
                } else {
                    System.out.println("Error: Input must be bigger than " + min + "!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Input must be an Integer!");
            }
        }
    }

    public String askForString(String prompt, int minLength, int maxLength) {
        while (true) {
            System.out.printf("%s (%d - %d): ", prompt, minLength, maxLength);
            String input = scanner.nextLine();

            if (input.trim().length() < minLength) {
                System.out.println("Error: Input too short! (Min " + minLength + " character)");
            } else if (input.length() > maxLength) {
                System.out.println("Error: Input too long! (Max " + maxLength + " character)");
            } else {
                return input; // Input valid
            }
        }
    }

    public <T extends Enum<T>> T askForEnum(String prompt, Class<T> enumClass) {
        T[] constants = enumClass.getEnumConstants();

        System.out.println(prompt);
        for (int i = 0; i < constants.length; i++) {
            System.out.println((i + 1) + ". " + constants[i].name());
        }

        int choice = askForInt("Choose option", 1, constants.length);
        return constants[choice - 1];
    }

    //===========================================================================================================================//

}
