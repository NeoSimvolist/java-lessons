package com.company.lesson6.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {

    public static final int PORT = 8189;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен, ожидаем подключения...");
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                new Thread(() -> threadHandler(socket, inputStream, outputStream)).start();
            }
        }
        catch (IOException e) {
            System.err.println("Ошибка подключения к порту " + PORT);
            e.printStackTrace();
        }
    }

    /**
     * Обработчик реализующий отправку сообщений клиенту
     */
    public static void sendMessageHandler(DataOutputStream outputStream) {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Введите сообщение клиенту: ");
                String message = scanner.nextLine();
                outputStream.writeUTF(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обработчик реализующий отправку эхо ответов клиенту
     */
    public static void threadHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        Thread sendMessageThread = new Thread(() -> sendMessageHandler(outputStream));
        sendMessageThread.start();

        System.out.println("Клиент подключился");
        try {
            outputStream.writeUTF("Сеанс запущен! Чтобы завершить сеанс отправьте /end");
            while (true) {
                String str = inputStream.readUTF();
                if (str.equals("/end")) {
                    outputStream.writeUTF("Сеанс завершен! Досвидания ;)");
                    break;
                }
                outputStream.writeUTF("Эхо: " + str);
            }
            socket.close();
        } catch (EOFException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("");
        System.out.println("Клиент отключился");
        sendMessageThread.stop();
    }
}
