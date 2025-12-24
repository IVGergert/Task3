package com.gergert.task3.entity;

import com.gergert.task3.state.VanState;
import com.gergert.task3.state.impl.VanNewStateImpl;

import java.util.Random;
import java.util.StringJoiner;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Van implements Callable<String> {
    private static final Random random = new Random();

    private final int id;
    private final boolean isPerishable;
    private final VanOperation operation;
    private VanState state;

    public Van(int id, boolean isPerishable, VanOperation operation) {
        this.id = id;
        this.isPerishable = isPerishable;
        this.operation = operation;
        this.state = new VanNewStateImpl();
    }

    public int getId() {
        return id;
    }

    public boolean isPerishable() {
        return isPerishable;
    }

    public String getState() {
        return state.getNameState();
    }

    public VanOperation getOperation() {
        return operation;
    }

    public void setState(VanState state) {
        this.state = state;
    }

    @Override
    public String call() {
        LogisticBase base = LogisticBase.getInstance();

        state.nextVan(this);

        Terminal terminal = base.getTerminal(this);

        if (terminal != null){
            state.nextVan(this);

            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1500) + 500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            state.nextVan(this);

            base.releaseTerminal(terminal);
        }

        return "Van " + id + " finished " + operation;
    }
}
