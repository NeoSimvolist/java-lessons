package com.company;

public class Cat implements Actor {
    static int ABILITY_TO_JUMP = 40;
    static int ABILITY_TO_RUN = 20;

    public int jump() {
        return ABILITY_TO_JUMP;
    }

    public int run() {
        return ABILITY_TO_RUN;
    }

    public String getName() {
        return "Cat";
    }
}
