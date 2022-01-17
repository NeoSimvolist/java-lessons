package com.company.lesson8.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setMinHeight(400);
        stage.setMinWidth(600);

        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("client-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Онлайн чат (by NeoSimvolist)");
        stage.setScene(scene);
        stage.show();

        ClientController clientController = fxmlLoader.getController();
        clientController.initialize(stage);
    }

    public static void main(String[] args) {
        Application.launch();
    }
}