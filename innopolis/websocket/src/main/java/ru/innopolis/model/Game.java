package ru.innopolis.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Game {
    private String targetWord;
    private List<Player> players = new ArrayList<>();
    private boolean gameOver;

    public Game(String targetWord) {
        this.targetWord = targetWord;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public Player getPlayer(String name) {
        return players.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
