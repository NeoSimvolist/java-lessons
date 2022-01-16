package com.company.lesson6.server;

import java.util.HashMap;
import java.util.Objects;

public class ServerApplication {

    public static final int PORT = 8189;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        HashMap<String, String> users = new HashMap<>();
        users.put("user1", "user1");
        users.put("user2", "user2");
        users.put("user3", "user3");
        server.onSignIn((userEntity) -> {
            if (users.get(userEntity.login) == null) return false;
            return Objects.equals(users.get(userEntity.login), userEntity.password);
        });
        server.run();
    }
}
