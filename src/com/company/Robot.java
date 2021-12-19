package com.company;

public class Robot implements Actor {
    public String name = "Robot";

    public void jump() {
        System.out.println(this.name + " jumping");
    }

    public void run() {
        System.out.println(this.name + " running");
    }
}
