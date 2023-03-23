package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Platform;
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
                        if (parts[0].equals("LOGIN_STATUS")) {
                            // login successful, zeigt success message und aktiviert das command field
                            if (parts[1].equals("SUCCESS")) {
                                statusLabel.setText("Logged in as " + usernameField.getText());
                                commandField.requestFocus();
                            }
                            // login failed
                            else {
                                statusLabel.setText("Login failed");
                            }
                        }
                        // wenn die message eine MY_NEWS message ist, dann zeigt es es in der message area
                        else if (parts[0].equals("MY_NEWS")) {
                            messagesArea.appendText(parts[1] + "\n");
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
}