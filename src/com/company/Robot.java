package com.company;

public class Robot implements Actor {
    static int ABILITY_TO_JUMP = 0;
    static int ABILITY_TO_RUN = 80;

    public int jump() {
        return ABILITY_TO_JUMP;
    }

    public int run() {
        return ABILITY_TO_RUN;
    }

    public String getName() {
        return "Robot";
    }
}
