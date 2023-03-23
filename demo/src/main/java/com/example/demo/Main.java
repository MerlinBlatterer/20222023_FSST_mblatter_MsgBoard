package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Main {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean fRunning = true;

    public void start(int port) throws IOException {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        serverSocket = new ServerSocket(port);
        System.out.println("Waiting for connection on port "+port);
        clientSocket = serverSocket.accept();
        System.out.println("Client connected");
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        int i = 0; // entfernen
        while(fRunning) {
            String inputLine;
            while (fRunning && (inputLine = in.readLine()) != null) {
                System.out.println("RECV: " + inputLine);
                String input = scanner.nextLine();
                System.out.println("SEND: " + input);
                if (input.compareTo("exit")==0){
                    fRunning = false;
                } else {
                    out.println(input);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Main testServer = new Main();
        testServer.start(6666);
    }
}