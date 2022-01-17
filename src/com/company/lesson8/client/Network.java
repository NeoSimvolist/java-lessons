package com.company.lesson8.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Consumer;

public class Network {

    private final String host;
    private final int port;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private Consumer<Boolean> changeConnectionStatusHandler;
    private Consumer<String> receiveMessageHandler;

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        this.socket = new Socket(this.host, this.port);
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    public void close() {
        if (!this.isConnected()) return;
        try {
            this.socket.close();
            this.socket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return this.socket != null && this.socket.isConnected();
    }

    public void sendMessage(String message) throws IOException {
        this.output.writeUTF(message);
    }

    /**
     * Подписка на изменение статуса подключения
     */
    public void onChangeConnectionStatus(Consumer<Boolean> changeConnectionStatusHandler) {
        this.changeConnectionStatusHandler = changeConnectionStatusHandler;
        this.emitChangeConnectionStatus();
    }

    /**
     * Подписка на получение сообщения
     */
    public void onReceiveMessage(Consumer<String> receiveMessageHandler) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String message = this.input.readUTF();
                    receiveMessageHandler.accept(message);
                } catch (IOException e) {
                    if (e instanceof EOFException || e instanceof SocketException) break;
                    e.printStackTrace();
                    break;
                }
            }
            this.close();
            this.emitChangeConnectionStatus();
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void emitChangeConnectionStatus() {
        if (changeConnectionStatusHandler == null) return;
        changeConnectionStatusHandler.accept(isConnected());
    }
}
