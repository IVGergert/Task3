package com.gergert.task3.state.impl;

import com.gergert.task3.entity.Van;
import com.gergert.task3.state.VanState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanWaitingStateImpl implements VanState {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void nextVan(Van van) {
        logger.info("Van {} moved from WAITING to PROCESSING.", van.getId());
        van.setState(new VanProcessingStateImpl());
    }

    @Override
    public String getNameState() {
        return "WAITING";
    }
}
