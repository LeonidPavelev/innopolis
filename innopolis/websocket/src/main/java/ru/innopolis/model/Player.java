package ru.innopolis.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Player {
    private String name;
    private List<String> guesses = new ArrayList<>();
    private boolean winner;

    public Player(String name) {
        this.name = name;
    }

    public void addGuess(String guess) {
        guesses.add(guess);
    }
}