package com.company;

public class Main {

    public static void main(String[] args) {
        Man man = new Man();
        man.jump();
        man.run();

        Cat cat = new Cat();
        cat.jump();
        cat.run();

        Robot robot = new Robot();
        robot.jump();
        robot.run();
    }
}
