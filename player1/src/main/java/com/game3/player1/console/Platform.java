package com.game3.player1.console;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class Platform {

    private static final Scanner scanner = new Scanner(System.in);

    public static void clearScreen() {
        try {
            //TODO
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {
            log.warn("platform error: {}", e.getMessage());
        }
    }

    public static void echo(Object message) {
        System.out.println(message);
    }

    public static String readLine() {
        return scanner.nextLine();
    }

    public static void pauseScreen(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {}
    }
}
