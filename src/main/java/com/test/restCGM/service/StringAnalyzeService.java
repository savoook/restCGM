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

    public Map<Character, List<Integer>> analyze(String str, Set<Character> chars, Statistic statistic, AtomicInteger requestCount) {
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
                List<Double> statInfo = statistic.getStatistic().get(c);
                statInfo.set(0, statInfo.get(0) + 1);
                statInfo.set(1, (statInfo.get(1)*statInfo.get(3) + countChar) / requestCount.get());
                statInfo.set(2, (statInfo.get(2)*statInfo.get(3) + countRepeate) / requestCount.get());
                statInfo.set(3, (double) requestCount.get());
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
