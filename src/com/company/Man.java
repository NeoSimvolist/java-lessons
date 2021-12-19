package com.company;

public class Man implements Actor {
    static int ABILITY_TO_JUMP = 20;
    static int ABILITY_TO_RUN = 40;

    public int jump() {
        return ABILITY_TO_JUMP;
    }

    public int run() {
        return ABILITY_TO_RUN;
    }

    public String getName() {
        return "Man";
    }
}
