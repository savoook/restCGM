package com.test.restCGM.controller;

import com.test.restCGM.entity.IncomingRequest;
import com.test.restCGM.entity.Statistic;
import com.test.restCGM.service.CorrectStatisticService;
import com.test.restCGM.service.DistinctCharacterService;
import com.test.restCGM.service.StringAnalyzeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private StringAnalyzeService stringAnalyzeService;
    private DistinctCharacterService distinctCharacterService;
    private CorrectStatisticService correctStatisticService;

    private static Statistic statistic = new Statistic();
    private static AtomicInteger requestCount = new AtomicInteger(0);

    public RestController(StringAnalyzeService stringAnalyzeService, DistinctCharacterService distinctCharacterService, CorrectStatisticService correctStatisticService) {
        this.stringAnalyzeService = stringAnalyzeService;
        this.distinctCharacterService = distinctCharacterService;
        this.correctStatisticService = correctStatisticService;
    }

    @PostMapping("/string")
    IncomingRequest analyze(@RequestBody String str) {
        IncomingRequest incomingRequest = new IncomingRequest(str);
        Set<Character> chars = distinctCharacterService.distinct(str);
        incomingRequest.setSymbols(stringAnalyzeService.analyze(str, chars, statistic, requestCount));
        correctStatisticService.correct(chars, statistic, requestCount);
        return incomingRequest;
    }

    @GetMapping("/statistic")
    Map<Character, List<Double>> statistic() {
        return statistic.getStatistic();
    }
}
