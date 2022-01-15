package com.example.chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ChatController {

    @FXML public ListView<String> listView;
    @FXML private TextArea textArea;
    @FXML public TextField textField;
    @FXML private Button sendButton;

    public void sendMessage(ActionEvent actionEvent) {
        if (this.textField.getText().isEmpty()) return;
        this.textArea.appendText(this.textField.getText());
        this.textArea.appendText(System.lineSeparator());
        this.textField.clear();
    }
}