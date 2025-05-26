package ru.innopolis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.innopolis.dto.GameStateDto;
import ru.innopolis.dto.PlayerDto;
import ru.innopolis.model.Game;
import ru.innopolis.model.Player;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final WorldService worldService;
    private Game currentGame;

    public void startNewGame() {
        String targetWord = worldService.getRandomWord();
        this.currentGame = new Game(targetWord);
    }

    public void addPlayer(String playerName) {
        if (currentGame == null) {
            startNewGame();
        }
        currentGame.addPlayer(new Player(playerName));
    }

    public void processGuess(String playerName, String guess) {
        Player player = currentGame.getPlayer(playerName);
        if (player != null) {
            player.addGuess(guess);
            if (guess.equalsIgnoreCase(currentGame.getTargetWord())) {
                player.setWinner(true);
                currentGame.setGameOver(true);
            }
        }
    }

    public GameStateDto getGameState() {
        List<PlayerDto> playerDTOs = currentGame.getPlayers().stream()
                .map(player -> new PlayerDto(
                        player.getName(),
                        player.getGuesses(),
                        player.isWinner()))
                .toList();

        return new GameStateDto(
                currentGame.getTargetWord(),
                playerDTOs,
                currentGame.isGameOver());
    }
}
