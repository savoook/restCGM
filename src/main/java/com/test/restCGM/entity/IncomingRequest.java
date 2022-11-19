package com.test.restCGM.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomingRequest {
    //@Id
    private String id;
    private String str;
    private Map<Character, List<Integer>> symbols = new HashMap<>();

    public IncomingRequest(String str) {
        this.str = str;
    }

    public void setSymbols(Map<Character, List<Integer>> symbols) {
        this.symbols = symbols;
    }
}
