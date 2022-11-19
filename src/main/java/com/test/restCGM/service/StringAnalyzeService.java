package com.test.restCGM.service;

import com.test.restCGM.entity.Statistic;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StringAnalyzeService {
    public StringAnalyzeService(CountCharactersService countCharactersService, RepeatingCharactersService repeatingCharactersService) {
        this.countCharactersService = countCharactersService;
        this.repeatingCharactersService = repeatingCharactersService;
    }

    private CountCharactersService countCharactersService;
    private RepeatingCharactersService repeatingCharactersService;

    public Map<Character, List<Integer>> alalyze(String str, Set<Character> chars, Statistic statistic, AtomicInteger requestCount) {
        Map<Character, List<Integer>> symbols = new HashMap<>();
        requestCount.getAndIncrement();
        chars.forEach(c ->
        {
            List<Integer> info = new ArrayList<>();
            int countChar = countCharactersService.count(str, c);
            int countRepeate = repeatingCharactersService.count(str, c);
            info.add(countChar);
            info.add(countRepeate);
            symbols.put(c, info);
            if (statistic.getStatistic().containsKey(c)) {
                List<Double> integers = statistic.getStatistic().get(c);
                integers.set(0, integers.get(0) + 1);
                integers.set(1, (integers.get(1) + countChar) / requestCount.get());
                integers.set(2, (integers.get(2) + countRepeate) / requestCount.get());
                integers.set(3, (double) requestCount.get());
            } else {
                statistic.getStatistic().put(c, new ArrayList<>() {{
                    add(1d);
                    add((double) countChar / (double) requestCount.get());
                    add((double) countRepeate / (double) requestCount.get());
                    add((double) requestCount.get());
                }});
            }
        });
        return symbols;
    }
}
