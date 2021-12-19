package com.company;

public class Treadmill implements Test {

    public String getName() {
        return "Treadmill";
    }

    public void passTheTest(Actor actor) {
        System.out.println("The " + this.getName() + " was successfully completed by a " + actor.getName());
    }
}
