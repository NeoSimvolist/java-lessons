package com.company;

public class Man implements Actor {
    public String name = "Man";

    public void jump() {
        System.out.println(this.name + " jumping");
    }

    public void run() {
        System.out.println(this.name + " running");
    }
}
