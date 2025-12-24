package com.gergert.task3.state;

import com.gergert.task3.entity.Van;

public interface VanState {
    void nextVan(Van van);
    String getNameState();
}
