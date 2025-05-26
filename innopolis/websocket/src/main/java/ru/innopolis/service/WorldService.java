package ru.innopolis.service;

import org.springframework.stereotype.Service;
import ru.innopolis.model.WorldDictionary;

import java.util.List;
import java.util.Random;
@Service
public class WorldService {

    public String getRandomWord() {
        List<String> words = WorldDictionary.getWords();
        return words.get(new Random().nextInt(words.size()));
    }
}
