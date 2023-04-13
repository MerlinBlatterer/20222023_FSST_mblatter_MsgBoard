package com.example.severmodul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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
                    System.out.println(ANSI_BLUE+msg+ANSI_RESET);
                    String[] args = msg.split("\t");
                    System.out.println("len: " + args.length + " " + Arrays.toString(args));
                    //login:
                    if(userName.equals("")){
                        switch (args[0]){
                            case "":
                                send("LOGIN_FAILED\tempty");
                                break;
                            case "LOGIN":
                                if (args.length!=3){
                                    send("LOGIN_FAILED\tinvalid command format");
                                }
                                if(fakeDB.login(args[1],args[2])==1){
                                    send("LOGIN_SUCCESS");
                                    userName=args[1];
                                }else{
                                    send("LOGIN_FAILED\tInvalid Username or Password");
                                }
                                break;
                            default:
                                send("LOGIN_FAILED\tCommand: " + args[0] + " is INVALID! are you logged in?");
                        }
                    }

                    //Logged in:

                    if(!userName.equals("")){
                        switch (args[0]){
                            case "TOPICS":
                                ArrayList<String> topics = fakeDB.getTopics(userName);
                                if(topics==null){
                                    send("TOPICS");
                                }else{
                                    StringBuilder sendstr = new StringBuilder();
                                    sendstr.append("TOPICS");
                                    out.print("TOPICS");
                                    for (String s:
                                         topics) {
                                        sendstr.append("\t").append(s);
                                    }
                                    send(sendstr.toString());
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

    private void send(String msg){
        System.out.println(ANSI_GREEN + msg + ANSI_RESET);
        out.println(msg);
    }
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
}
