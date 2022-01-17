package com.company.lesson8.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.function.Function;

public class Server {

    // Время за которое доджен авторизоваться клиент
    public static final int KEEP_ALIVE_UNTIL_AUTH_MS = 120000;

    public class Session {
        private boolean isAuth;

        synchronized public void setIsAuth(boolean value) {
            this.isAuth = value;
        }

        synchronized public boolean getIsAuth() {
            return this.isAuth;
        }
    }

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
                Session session = new Session();
                new Thread(() -> sessionHandler(socket, session)).start();
                new Thread(() -> closeNotAuthSessionHandler(socket, session)).start();
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
    private void sessionHandler(Socket socket, Session session) {
        System.out.println("Клиент подключился");
        String login = null;
        try {
            try {
                login = this.authHandler(socket);
                session.setIsAuth(true);
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
     * Обработчик закрывающий соединение с клиентом если пользователь не авторизовался за KEEP_ALIVE_UNTIL_AUTH
     */
    private void closeNotAuthSessionHandler(Socket socket, Session session) {
        try {
            Thread.sleep(KEEP_ALIVE_UNTIL_AUTH_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!session.getIsAuth()) {
            try {
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.writeUTF("Вы не авторизовались за " + (KEEP_ALIVE_UNTIL_AUTH_MS / 1000) + " сек. Соединение разорвано!");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
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
            outputStream.writeUTF("Отправьте логин и пароль (через пробел):");
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
