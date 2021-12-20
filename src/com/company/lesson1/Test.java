package com.company.lesson1;

public abstract class Test {
    public abstract String getName();

    abstract int getHeight();

    abstract int getLength();

    public Boolean passTheTest(Actor actor) {
        return actor.jump() >= this.getHeight() && actor.run() >= this.getLength();
    }
}
