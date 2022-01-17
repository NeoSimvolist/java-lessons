package com.company.lesson8.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ClientController {

    public static final String HOST = "localhost";
    public static final int PORT = 8189;

    @FXML
    private ListView<String> listView;
    @FXML
    private TextField textField;
    @FXML
    private TextArea textArea;
    @FXML
    private Button sendButton;
    @FXML
    private Label caption;

    private Network network;

    /**
     * Хук. Сработает после инициализации контроллера
     */
    public void initialize(Stage stage) {
        this.listView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, login) -> this.textField.setText("/w " + login + " "));
        this.network = new Network(HOST, PORT);

        try {
            this.network.connect();
        } catch (IOException e) {
            e.printStackTrace();
            String error = "Не удалось подключиться к серверу";
            this.showErrorDialog(error);
        }

        // Регистрируем обработчик закрытия приложения
        stage.setOnCloseRequest(windowEvent -> this.network.close());
        // Регистрируем обработчик изменения статуса подключения
        network.onChangeConnectionStatus(isConnected -> Platform.runLater(() -> onChangeConnectionStatus(isConnected)));
        // Регистрируем обработчик получения сообщений от сервера
        network.onReceiveMessage(message -> Platform.runLater(() -> onReceiveMessage(message)));
    }

    public void sendMessage() {
        if (this.textField.getText().isEmpty()) return;
        try {
            this.network.sendMessage(this.textField.getText());
            this.textField.clear();
        } catch (IOException e) {
            e.printStackTrace();
            String error = "Не удалось отправить сообщение";
            this.showErrorDialog(error);
        }
    }

    /**
     * Обработчик изменения статуса подключения
     */
    private void onChangeConnectionStatus(boolean isConnected) {
        this.listView.setDisable(!isConnected);
        this.textField.setDisable(!isConnected);
        this.sendButton.setDisable(!isConnected);
        this.caption.setText(isConnected ? "Онлайн чат: подключено" : "Онлайн чат: отключено");
        if (isConnected) {
            this.textField.requestFocus();
        }
    }

    private void onReceiveMessage(String message) {
        // Обработка системного сообщения. Список пользователей онлайн
        if (message.startsWith("/users")) {
            this.listView.getItems().clear();
            String[] parts = message.split(" ");
            for (String part : parts) {
                if (Objects.equals(part, "/users")) continue;
                this.listView.getItems().add(part);
            }
            return;
        }
        // Отображаем сообщения полученные сервера
        this.textArea.appendText(message);
        this.textArea.appendText(System.lineSeparator());
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }
}