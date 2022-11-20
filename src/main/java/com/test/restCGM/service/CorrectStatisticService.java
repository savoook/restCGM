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
                List<Double> statInfo = entry.getValue();
                statInfo.set(1, statInfo.get(1) * statInfo.get(3) / requestCount.get());
                statInfo.set(2, statInfo.get(2) * statInfo.get(3) / requestCount.get());
                statInfo.set(3, (double) requestCount.get());
            }
        }
    }
}
