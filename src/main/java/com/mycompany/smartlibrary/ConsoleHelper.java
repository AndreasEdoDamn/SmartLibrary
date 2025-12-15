package com.mycompany.smartlibrary;

import java.util.Scanner;

public class ConsoleHelper {

    Scanner scanner;

    public ConsoleHelper() {
        scanner = new Scanner(System.in);
    }

    public void printLogo() {
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

    public void printOptions() {
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

    public void printBulk(char c, int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(c);
        }
        System.out.println();
    }

    public int askForInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Error: Input harus antara " + min + " dan " + max + "!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Input harus berupa angka (Integer)!");
            }
        }
    }

    public String askForString(String prompt, int minLength, int maxLength) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.trim().length() < minLength) {
                System.out.println("Error: Input terlalu pendek! (Min " + minLength + " karakter)");
            } else if (input.length() > maxLength) {
                System.out.println("Error: Input terlalu panjang! (Max " + maxLength + " karakter)");
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

        int choice = askForInt("Pilih opsi (angka): ", 1, constants.length);
        return constants[choice - 1];
    }
}
