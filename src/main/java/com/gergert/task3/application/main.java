package com.gergert.task3.application;

import com.gergert.task3.entity.Van;
import com.gergert.task3.parser.impl.VanParserImpl;
import com.gergert.task3.reader.impl.ReadDataFromFileImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Starting testing application");

        ReadDataFromFileImpl readData = new ReadDataFromFileImpl();
        VanParserImpl parser = new VanParserImpl();

        List<String> lines = readData.read("data/vans.txt");

        if (lines.isEmpty()) {
            logger.error("No data found.");
            return;
        }

        List<Van> vans = parser.parse(lines);

        ExecutorService executor = Executors.newCachedThreadPool();

        for (Van van : vans) {
            executor.submit(van);
        }

        executor.shutdown();
        try {
            if (executor.awaitTermination(60, TimeUnit.SECONDS)) {
                logger.info("All operations completed.");
            } else {
                executor.shutdownNow(); // Если зависли - убиваем принудительно
            }
        } catch (InterruptedException e){
            executor.shutdown();
        }

    }
}
