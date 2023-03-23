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

    @FXML
    private void commandFieldSubmitted() {
        // Send the
