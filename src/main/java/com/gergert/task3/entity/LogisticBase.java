package com.gergert.task3.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticBase {
    private static final Logger logger = LogManager.getLogger();
    private static final int TERMINAL_COUNT = 5;

    private final Queue<Terminal> freeTerminals;
    private final Lock lock = new ReentrantLock(true);

    private final Condition normalQueue = lock.newCondition();
    private final Condition perishableQueue = lock.newCondition();

    private int waitingPerishablesCount;

    private LogisticBase() {
        freeTerminals = new LinkedList<>();
        for (int i = 1; i <= TERMINAL_COUNT; i++) {
            freeTerminals.add(new Terminal(i));
        }
    }

    private static class SingletonHolder {
        private static final LogisticBase INSTANCE = new LogisticBase();
    }

    public static LogisticBase getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Terminal getTerminal(Van van) {
        lock.lock();
        try {
            boolean isPerishable = van.isPerishable();

            if (isPerishable) {
                waitingPerishablesCount++;
                while (freeTerminals.isEmpty()) {
                    logger.debug("Van {} perishable waits for terminal.", van.getId());
                    perishableQueue.await();
                }
                waitingPerishablesCount--;
            } else {
                while (freeTerminals.isEmpty() || waitingPerishablesCount > 0) {
                    logger.debug("Van {} normal waits.", van.getId());
                    normalQueue.await();
                }
            }

            Terminal terminal = freeTerminals.poll();
            logger.info("Van {} acquired {}.", van.getId(), terminal);
            return terminal;

        } catch (InterruptedException e) {
            logger.error("Wait interrupted", e);
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void releaseTerminal(Terminal terminal) {
        lock.lock();
        try {
            freeTerminals.add(terminal);
            logger.info("{} released.", terminal);

            if (waitingPerishablesCount > 0) {
                perishableQueue.signal();
            } else {
                normalQueue.signal();
            }
        } finally {
            lock.unlock();
        }
    }




}
