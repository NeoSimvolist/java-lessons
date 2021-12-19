package com.company;

public class Cat implements Actor {
    public String name = "Cat";

    public void jump() {
        System.out.println(this.name + " jumping");
    }

    public void run() {
        System.out.println(this.name + " running");
    }
}
