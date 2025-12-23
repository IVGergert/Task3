package com.gergert.task3.entity;

import java.util.StringJoiner;

public class Terminal {
    private final int id;

    public Terminal(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Terminal.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }
}
