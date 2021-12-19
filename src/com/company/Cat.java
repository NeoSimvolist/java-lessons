package com.company;

public class Cat implements Actor {
    public void jump() {
        System.out.println(this.getName() + " jumping");
    }

    public void run() {
        System.out.println(this.getName() + " running");
    }

    public String getName() {
        return "Cat";
    }
}
