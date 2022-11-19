package com.test.restCGM.entity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Statistic {

    private final Map<Character, List<Double>> statistic = new ConcurrentHashMap<>();

    public Map<Character, List<Double>> getStatistic() {
        return statistic;
    }
}
