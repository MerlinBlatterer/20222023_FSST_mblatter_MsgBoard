package com.example.client;

import java.util.Scanner;

public class Client {

    private static final String USERNAME = "user123";
    private static final String PASSWORD = "pass123";

    public static void main(String[] args) {
        // mit Server verbinden

        Scanner scanner = new Scanner(System.in);

        System.out.print("Benutzername: ");
        String username = scanner.nextLine();

        System.out.print("Passwort: ");
        String password = scanner.nextLine();

        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            System.out.println("Login erfolgreich!");
        } else {
            System.out.println("Benutzername oder Passwort falsch!");
        }
    }
    // An server senden
    // Nachrichten empfangen
}
