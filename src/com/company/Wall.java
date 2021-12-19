package com.company;

public class Wall implements Test {
    public String getName() {
        return "Wall";
    }

    public void passTheTest(Actor actor) {
        System.out.println("The " + this.getName() + " was successfully completed by a " + actor.getName());
    }
}
