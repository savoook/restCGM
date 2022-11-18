package com.test.restCGM.service;

import org.springframework.stereotype.Service;

@Service
public class RepeatingCharactersService {
    public int count(String str, Character character) {
        char[] chars = str.toCharArray();
        int count = 0;
        int max = count;
        for (int j = 0; j < chars.length; j++) {
            if (chars[j] == character) {
                count++;
                max = Math.max(max, count);
            } else {
                count = 0;
            }
        }
        return max;
    }
}