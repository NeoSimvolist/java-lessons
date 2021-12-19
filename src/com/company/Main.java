package com.company;

public class Main {

    public static void main(String[] args) {
        Actor[] actors = {new Man(), new Cat(), new Robot()};
        for (int index = 0; index < 3; index++) {
            actors[index].run();
            actors[index].jump();
        }
    }
}
