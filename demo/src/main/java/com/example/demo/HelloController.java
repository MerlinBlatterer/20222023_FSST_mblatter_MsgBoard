package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 6666;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label statusLabel;
    @FXML
    private TextArea messagesArea;
    @FXML
    private TextField commandField;

    private Thread receiver;

    @FXML
    private void initialize() {
        // Deaktiviert alle Felder außer username und passwort
        setFieldsEnabled(false);
    }

    @FXML
    private void loginButtonClicked() {
        // Connect to the server and log in
        String username = usernameField.getText();
        String password = passwordField.getText();
        String message = "LOGIN " + username + " " + password;
        sendToServer(message);
    }

    private void setFieldsEnabled(boolean enabled) {
        usernameField.setDisable(!enabled);
        passwordField.setDisable(!enabled);
        commandField.setDisable(!enabled);
    }

    private void sendToServer(String message) {
        out.println(message);
        out.flush();
    }

    public void start() {
        try {
            clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            setFieldsEnabled(true);

            // Startet einen neuen thread um die nachrichten vom server zu erhalten
            receiver = new Thread(() -> {
                try {
                    String message = in.readLine();
                    while (message != null) {
                        System.out.println("Server: " + message);
                        String[] parts = message.split("\t");
                        switch (parts[0]) {
                            case "LOGIN_STATUS" -> handleLoginStatus(parts[1]);
                            case "MY_NEWS" -> handleMyNews(parts[1]);
                            case "TOPICS" -> handleTopics(parts[1]);
                            default -> System.out.println("Unknown message type: " + parts[0]);
                        }
                        message = in.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            clientSocket.close();
            out.close();
            in.close();
            receiver.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLoginStatus(String status) {
        if (status.equals("SUCCESS")) {
            statusLabel.setText("Logged in as " + usernameField.getText());
            commandField.requestFocus();
        } else {
            statusLabel.setText("Login failed");
        }
    }

    private void handleMyNews(String news) {
        messagesArea.appendText(news + "\n");
    }

    private void handleTopics(String topics) {
        messagesArea.appendText("Available topics: " + topics + "\n");
    }

    @FXML
    private void handleCommand() {
        String command = commandField.getText();
        String[] parts = command.split(" ");
        String message;
        switch (parts[0].toUpperCase()) {
            case "NEWS":
                if (parts.length > 1) {
                    message = "NEWS " + parts[1];
                    sendToServer(message);
                } else {
                    messagesArea.appendText("Please specify a topic\n");
                }
                break;
            case "SUBSCRIBE":
                if (parts.length > 1) {
                    message = "SUBSCRIBE " + parts[1];
                    sendToServer(message);
                    break;
                }
            case "UNSUBSCRIBE":
                if (parts.length > 1) {
                    message = "UNSUBSCRIBE " + parts[1];
                    sendToServer(message);
                } else {
                    messagesArea.appendText("Please specify a topic\n");
                }
                break;
            case "TOPICS":
                sendToServer("TOPICS");
                break;
            default:
                messagesArea.appendText("Unknown command\n");
        }
        commandField.clear();
    }
}
