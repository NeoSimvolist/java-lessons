package com.company;

public class Main {

    public static void main(String[] args) {
        Test[] tests = {new Wall(), new Treadmill()};
        Actor[] actors = {new Man(), new Cat(), new Robot()};
        for (int testIndex = 0; testIndex < 2; testIndex++) {
            for (int actorIndex = 0; actorIndex < 3; actorIndex++) {
                tests[testIndex].passTheTest(actors[actorIndex]);
            }
        }
    }
}
