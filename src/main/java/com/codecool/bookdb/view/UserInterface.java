package com.codecool.bookdb.view;

import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Date;
import java.util.Scanner;

public class UserInterface {
    Scanner scanner;
    PrintStream out;

    public UserInterface(InputStream in, PrintStream out) {
        this.scanner = new Scanner(in);
        this.out = out;
    }

    public void println(Object obj) {
        out.println(obj);
    }

    public void printTitle(String title) {
        out.println("\n -- " + title + " --");
    }

    public void printOption(char option, String description) {
        out.println("(" + option + ")" + " " + description);
    }

    public char choice(String options) {
        String line;
        do {
            out.print("Choice [" + options + "]: ");
            line = scanner.nextLine();
        } while (!(line.length() == 1 && options.contains(line)));
        return line.charAt(0);
    }

    public String readString(String prompt, String defaultValue) {
        printPrompt(prompt, defaultValue);
        String line = scanner.nextLine();
        return line.isEmpty() ? defaultValue : line;
    }

    private void printPrompt(String prompt, Object defaultValue) {
        System.out.print(prompt + " [" + defaultValue + "]: ");
    }

    public Date readDate(String prompt, Date defaultValue) {
        while (true) {
            printPrompt(prompt, defaultValue);
            String line = scanner.nextLine();

            if (line.isEmpty()) {
                return defaultValue;
            }

            try {
                return Date.valueOf(line);
            } catch (IllegalArgumentException e) {
                out.println("Bad date format!");
            }
        }
    }

    public int readInt(String prompt, int defaultValue) {
        while (true) {
            printPrompt(prompt, defaultValue);
            String line = scanner.nextLine();

            if (line.isEmpty())
                return defaultValue;

            try {
                return Integer.valueOf(line);
            } catch (IllegalArgumentException e) {
                out.println("Enter an integer!");
            }
        }
    }

}