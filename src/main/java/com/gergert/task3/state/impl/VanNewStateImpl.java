package com.gergert.task3.state.impl;

import com.gergert.task3.entity.Van;
import com.gergert.task3.state.VanState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanNewStateImpl implements VanState {
    private static final Logger logger = LogManager.getLogger();

    public void nextVan(Van van){
        logger.info("Van {} is initialized and heading to the Logistic Base.", van.getId());
        van.setState(new VanWaitingStateImpl());
    }
}
