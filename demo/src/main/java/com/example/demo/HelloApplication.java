package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        HelloController controller = fxmlLoader.getController();
        controller.start();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello Application");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        // Controller stop method aufrufen, um die socket-verbindung zu schließen
        ((HelloController)FXMLLoader.load(getClass().getResource("hello-view.fxml"))).stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}