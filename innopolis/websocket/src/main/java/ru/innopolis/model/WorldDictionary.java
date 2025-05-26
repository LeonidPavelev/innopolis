package ru.innopolis.model;

import java.util.Arrays;
import java.util.List;

public class WorldDictionary {
    private static final List<String> WORDS = Arrays.asList(
            "APPLE", "BEACH", "CHAIR", "DANCE", "EAGLE",
            "FLAME", "GHOST", "HONEY", "IGLOO", "JUMBO"
    );

    public static List<String> getWords() {
        return WORDS;
    }
}