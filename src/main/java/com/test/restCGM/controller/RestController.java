package com.test.restCGM.controller;

import com.test.restCGM.entity.IncomingRequest;
import com.test.restCGM.entity.Statistic;
import com.test.restCGM.service.CountCharactersService;
import com.test.restCGM.service.DistinctCharacterService;
import com.test.restCGM.service.RepeatingCharactersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final CountCharactersService countCharactersService;
    private final RepeatingCharactersService repeatingCharactersService;
    private final DistinctCharacterService distinctCharacterService;

    private static Statistic statistic = new Statistic();

    private static AtomicInteger requestCount = new AtomicInteger(0);

    public RestController(CountCharactersService countCharactersService, RepeatingCharactersService repeatingCharactersService, DistinctCharacterService distinctCharacterService) {
        this.countCharactersService = countCharactersService;
        this.repeatingCharactersService = repeatingCharactersService;
        this.distinctCharacterService = distinctCharacterService;
    }

    @PostMapping("/string")
    IncomingRequest analyze(@RequestBody String str) {
        IncomingRequest incomingRequest = new IncomingRequest(str);
        Set<Character> chars = distinctCharacterService.distinct(str);
        Map<Character, List<Integer>> symbols = new HashMap<>();
        requestCount.getAndIncrement();
        chars.forEach(c -> {
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
            } else {
                statistic.getStatistic().put(c, new ArrayList<>() {{
                    add(1d);
                    add((double)countChar);
                    add((double)countRepeate);
                }});
            }
        });
        incomingRequest.setSymbols(symbols);

        return incomingRequest;
    }

    @GetMapping("/statistic")
    Map<Character, List<Double>> statistic() {
        return statistic.getStatistic();
    }
}
