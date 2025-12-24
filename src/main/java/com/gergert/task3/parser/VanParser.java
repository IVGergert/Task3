package com.gergert.task3.parser;

import com.gergert.task3.entity.Van;

import java.util.List;

public interface VanParser {
    List<Van> parse(List<String> lines);
}
