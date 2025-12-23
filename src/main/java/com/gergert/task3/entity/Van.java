package com.gergert.task3.entity;

import com.gergert.task3.state.VanState;

import java.util.concurrent.Callable;

public class Van implements Callable<String> {
    private final int id;
    private final boolean isPerishable;
    private VanState state;

    public Van(int id, boolean isPerishable) {
        this.id = id;
        this.isPerishable = isPerishable;
    }

    @Override
    public String call() throws Exception {
        return "";
    }

    public int getId() {
        return id;
    }

    public boolean isPerishable() {
        return isPerishable;
    }

    public VanState getState() {
        return state;
    }

    public void setState(VanState state) {
        this.state = state;
    }
}
