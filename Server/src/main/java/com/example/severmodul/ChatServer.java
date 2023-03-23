package com.example.severmodul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

 // Nachrichten senden/empfangen

public class ChatServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("created socket. waiting for client...");
            clientSocket = serverSocket.accept();
            System.out.println("Connected");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            /*String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }*/
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
                out.println(msg);
                out.flush();
            }
        }
    });

    Thread receiver = new Thread(new Runnable() {
        String msg;
        @Override
        public void run() {
            FakeDB fakeDB = new FakeDB();
            String userName = "";
            try {
                msg = in.readLine();
                while(msg != null){
                    System.out.println("Client: "+msg);
                    String[] args = msg.split("\t");
                    System.out.println(args.length + " " + args[0]);
                    if(userName.equals("")){
                        switch (args[0]){
                            case "":
                                System.err.println("empty");;
                                break;
                            case "LOGIN":
                                if (args.length!=3){
                                    System.out.println("s: LOGIN_FAILED");
                                    out.println("LOGIN_FAILED");
                                }
                                if(fakeDB.login(args[1],args[2])==1){
                                    System.out.println("s: LOGIN_SUCCESS");
                                    out.println("LOGIN_SUCCESS");
                                    userName=args[1];
                                }
                                break;
                            default:
                                System.out.println(args[0] + " is INVALID! are you logged in?");
                        }
                    }
                    if(!userName.equals("")){
                        switch (args[0]){
                            case "TOPICS":
                                ArrayList<String> topics = fakeDB.getTopics(userName);
                                if(topics==null){
                                    out.println("TOPICS");
                                }else{
                                    out.print("TOPICS");
                                    for (String s:
                                         topics) {
                                        out.print("\t"+s);
                                    }
                                    out.println();
                                }
                                break;
                            default:
                                System.out.println("defaulted in LoggedIn switch");
                        }
                    }

                    msg = in.readLine();
                }
                System.out.println("Client disconnected");
                out.close();
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    });

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start(6666);
        //server.sender.start();
        server.receiver.start();
    }
}
