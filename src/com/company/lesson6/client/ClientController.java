package com.company.lesson6.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

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
        network.onReceiveMessage(message -> Platform.runLater(() -> appendMessage(message, "Сервер")));
    }

    public void sendMessage() {
        if (this.textField.getText().isEmpty()) return;
        this.appendMessage(this.textField.getText(), "Я");
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
        this.textField.setDisable(!isConnected);
        this.sendButton.setDisable(!isConnected);
        this.caption.setText(isConnected ? "Онлайн чат: подключено" : "Онлайн чат: отключено");
        this.listView.getItems().clear();
        if (isConnected) {
            this.textField.requestFocus();
            this.listView.getItems().addAll("User 1", "User 2");
        }
    }

    private void appendMessage(String message, String from) {
        this.textArea.appendText(from + ": " + message);
        this.textArea.appendText(System.lineSeparator());
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }
}