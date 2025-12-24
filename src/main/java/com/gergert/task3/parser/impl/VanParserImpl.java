package com.gergert.task3.parser.impl;

import com.gergert.task3.entity.Van;
import com.gergert.task3.entity.VanOperation;
import com.gergert.task3.parser.VanParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class VanParserImpl implements VanParser {
    private static final Logger logger = LogManager.getLogger();
    private static final String DELIMITER_REGEX = ",";

    @Override
    public List<Van> parse(List<String> lines) {
        List<Van> vans = new ArrayList<>();

        for (String line : lines){
            String[] parts = line.split(DELIMITER_REGEX);

            int id = Integer.parseInt(parts[0].trim());
            boolean isPerishable = Boolean.parseBoolean(parts[1].trim());
            VanOperation operation = VanOperation.valueOf(parts[2].trim().toUpperCase());
            vans.add(new Van(id, isPerishable, operation));
        }

        logger.info("Parsed {} vans.", vans.size());
        return vans;
    }
}
