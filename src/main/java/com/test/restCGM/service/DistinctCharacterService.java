package com.test.restCGM.service;

import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class DistinctCharacterService {
    public Set<Character> distinct(String str) {
        char[] chars = str.toCharArray();
        Set<Character> charSet = new LinkedHashSet<>();
        for (char c : chars) {
            charSet.add(c);
        }
        return charSet;
    }
}
