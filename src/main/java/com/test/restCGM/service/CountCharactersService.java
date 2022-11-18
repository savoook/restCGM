package com.test.restCGM.service;

import org.springframework.stereotype.Service;

@Service
public class CountCharactersService {
    public int count(String str, Character character) {
        char[] chars = str.toCharArray();
        int count = 0;
        for (Character c : chars) {
            if (c == character) count++;
        }
        return count;
    }
}
