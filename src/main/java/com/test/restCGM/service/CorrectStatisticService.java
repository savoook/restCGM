package com.test.restCGM.service;

import com.test.restCGM.entity.Statistic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CorrectStatisticService {
    public void correct(Set<Character> chars, Statistic statistic, AtomicInteger requestCount) {
        for (Map.Entry<Character, List<Double>> entry : statistic.getStatistic().entrySet()) {
            if (!chars.contains(entry.getKey())) {
                List<Double> value = entry.getValue();
                value.set(1, value.get(1) * value.get(3) / requestCount.get());
                value.set(2, value.get(2) * value.get(3) / requestCount.get());
                value.set(3, (double) requestCount.get());
            }
        }
    }
}
