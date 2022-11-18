package com.test.restCGM.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Statistic {

    int requestCount;
    private final Map<Character, List<Double>> statistic = new ConcurrentHashMap<>();

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public Map<Character, List<Double>> getStatistic() {
        return statistic;
    }
}
