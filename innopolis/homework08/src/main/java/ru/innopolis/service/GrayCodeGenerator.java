package ru.innopolis.service;

import java.util.stream.Stream;

public class GrayCodeGenerator {
    public static Stream<Integer> cycleGrayCode(int n) {
        if (n < 1 || n > 16) {
            throw new IllegalArgumentException("n should be between 1 and 16");
        }

        return Stream.iterate(0, i -> (i + 1) % (1 << n))
                .map(i -> i ^ (i >> 1));
    }
}