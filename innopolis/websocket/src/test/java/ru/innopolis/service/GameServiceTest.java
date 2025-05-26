package ru.innopolis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.innopolis.dto.GameStateDto;
import ru.innopolis.dto.PlayerDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private WorldService worldService;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        when(worldService.getRandomWord()).thenReturn("APPLE");
    }

    @Test
    void startNewGameShouldInitializeGameWithRandomWord() {
        gameService.startNewGame();
        GameStateDto state = gameService.getGameState();

        assertEquals("APPLE", state.getCurrentWord());
        assertFalse(state.isGameOver());
        assertTrue(state.getPlayers().isEmpty());
    }

    @Test
    void addPlayerShouldStartNewGameIfNotExists() {
        gameService.addPlayer("player");

        GameStateDto state = gameService.getGameState();
        assertEquals(1, state.getPlayers().size());
        assertEquals("player", state.getPlayers().get(0).getName());
    }

    @Test
    void processGuessCorrectGuessShouldMarkPlayerAsWinner() {
        gameService.addPlayer("player");
        gameService.processGuess("player", "APPLE");

        GameStateDto state = gameService.getGameState();
        assertTrue(state.isGameOver());
        assertTrue(state.getPlayers().get(0).isWinner());
    }

    @Test
    void processGuessIncorrectGuessShouldAddGuessToPlayer() {
        gameService.addPlayer("player");
        gameService.processGuess("player", "WRONG");

        GameStateDto state = gameService.getGameState();
        assertFalse(state.isGameOver());
        assertEquals(1, state.getPlayers().get(0).getGuesses().size());
        assertEquals("WRONG", state.getPlayers().get(0).getGuesses().get(0));
    }

    @Test
    void getGameStateShouldReturnCorrectState() {
        gameService.addPlayer("playerONE");
        gameService.addPlayer("playerTWO");
        gameService.processGuess("playerONE", "GUESS");

        GameStateDto state = gameService.getGameState();

        assertEquals("APPLE", state.getCurrentWord());
        assertFalse(state.isGameOver());
        assertEquals(2, state.getPlayers().size());

        PlayerDto player1 = state.getPlayers().stream()
                .filter(p -> p.getName().equals("playerONE"))
                .findFirst()
                .orElseThrow();
        assertEquals(1, player1.getGuesses().size());
        assertFalse(player1.isWinner());
    }
}