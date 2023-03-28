package com.example.severmodul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    Thread sender = new Thread(new Runnable() {
        String msg;
        Scanner input = new Scanner(System.in);
        @Override
        public void run() {
            while(true){
                msg = input.nextLine();
                //msg = "LOGIN\tUsername\tpassword";
                msg = msg.replaceAll("\\\\t","\t");
                out.println(msg);
                out.flush();
            }
        }
    });

    Thread receiver = new Thread(new Runnable() {
        String msg;
        @Override
        public void run() {
            try {
                msg = in.readLine();
                while(msg != null){
                    System.out.println("Server: "+msg);
                    msg = in.readLine();
                }
                System.out.println("Server disconnected");
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    });

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.startConnection("127.0.0.1", 6666);
        client.sender.start();
        client.receiver.start();
    }
}
