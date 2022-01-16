package com.company.lesson6.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.function.Function;

public class Server {
    public final Map<String, Socket> users = new HashMap<>();

    private final int port;
    private Function<UserEntity, Boolean> signInHandler;

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Сервер запущен, ожидаем подключения...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> sessionHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onSignIn(Function<UserEntity, Boolean> signInHandler) {
        this.signInHandler = signInHandler;
    }

    /**
     * Обработчик сессий клиентов
     */
    private void sessionHandler(Socket socket) {
        System.out.println("Клиент подключился");
        String login = null;
        try {
            try {
                login = this.authHandler(socket);
                this.messagesHandler(login, socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            System.out.println("Клиент отключился");
            if (login != null) {
                this.users.remove(login);
                this.sendBroadcastMessage(login + " вышел из чата");
                this.sendBroadcastMessage("/users " + String.join(" ", this.users.keySet()));
            }
        }
    }

    /**
     * Обработчик авторизации
     */
    private String authHandler(Socket socket) throws IOException {
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        String login;
        while (true) {
            outputStream.writeUTF("Введите логин и пароль:");
            String message = inputStream.readUTF();
            String[] messageParts = message.split(" ");
            if (messageParts.length != 2) {
                outputStream.writeUTF("Неверные логин или пароль");
                continue;
            }

            login = messageParts[0];
            String password = messageParts[1];
            if (!signInHandler.apply(new UserEntity(login, password))) {
                outputStream.writeUTF("Неверные логин или пароль");
                continue;
            }
            if (this.users.get(login) != null) {
                outputStream.writeUTF("Пользователь " + login + " уже авторизован!");
                continue;
            }
            break;
        }
        this.users.put(login, socket);
        this.sendBroadcastMessage(login + " вошел в чат");
        this.sendBroadcastMessage("/users " + String.join(" ", this.users.keySet()));
        return login;
    }

    /**
     * Обработчик сообщений
     */
    private void messagesHandler(String login, Socket socket) throws IOException {
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        while (true) {
            String message = inputStream.readUTF();
            // Приватные сообщения
            if (message.startsWith("/w")) {
                String[] parts = message.split(" ");
                if (parts.length < 2) {
                    outputStream.writeUTF("Неверный формат сообщения (пример: /w user1 Привет)");
                    continue;
                }
                String destinationLogin = parts[1];
                if (Objects.equals(destinationLogin, login)) {
                    outputStream.writeUTF("Нельзя написать самому себе");
                    continue;
                }
                if (users.get(destinationLogin) == null) {
                    outputStream.writeUTF("Пользователь " + destinationLogin + " не в сети");
                    continue;
                }
                String destinationMessage = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
                outputStream.writeUTF(login + "(для " + destinationLogin + "): " + destinationMessage);
                this.sendMessageToSocket(users.get(destinationLogin), login + "(Вам): " + destinationMessage);
                continue;
            }
            // Публичные сообщения
            this.sendBroadcastMessage(login + ": " + message);
        }
    }

    private void sendBroadcastMessage(String message) {
        this.users.values().forEach(socket -> this.sendMessageToSocket(socket, message));
    }

    private void sendMessageToSocket(Socket socket, String message) {
        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
