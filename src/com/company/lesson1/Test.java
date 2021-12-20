package com.company.lesson1;

public abstract class Test {
    abstract String getName();

    abstract int getHeight();

    abstract int getLength();

    Boolean passTheTest(Actor actor) {
        return actor.jump() >= this.getHeight() && actor.run() >= this.getLength();
    }
}
