package com.example.chat;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setMinHeight(400);
        stage.setMinWidth(600);

        FXMLLoader fxmlLoader = new FXMLLoader(ChatApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Онлайн чат (by NeoSimvolist)");
        stage.setScene(scene);

        ChatController controller = fxmlLoader.getController();
        controller.listView.getItems().add("User 1");
        controller.listView.getItems().add("User 2");
        Platform.runLater(() -> controller.textField.requestFocus());

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}