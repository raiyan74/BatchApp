package com.example.batchapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class BatchEditorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BatchEditorApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 680, 680);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        // Load the icon image
        Image icon = new Image("file:icon/icon.png");
        stage.getIcons().add(icon);

        stage.setTitle("AutoGo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}