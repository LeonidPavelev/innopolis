package ru.innopolis.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.innopolis.dto.GameMessageDto;
import ru.innopolis.dto.GameStateDto;
import ru.innopolis.service.GameService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WebSocketControllerTest {

    @Autowired
    private WebSocketController webSocketController;

    @MockBean
    private GameService gameService;

    @Test
    void sendMessageShouldProcessGuessAndReturnState() {
        GameMessageDto message = new GameMessageDto("guess", "APPLE", "player1");
        GameStateDto expectedState = new GameStateDto("APPLE", List.of(), true);

        when(gameService.getGameState()).thenReturn(expectedState);

        GameStateDto result = webSocketController.sendMessage(message);

        verify(gameService).processGuess("player1", "APPLE");
        assertEquals(expectedState, result);
    }

    @Test
    void addPlayerShouldCallGameService() {
        GameMessageDto message = new GameMessageDto("JOIN", "", "Player1");
        GameStateDto expectedState = new GameStateDto("APPLE", List.of(), false);

        when(gameService.getGameState()).thenReturn(expectedState);

        GameStateDto result = webSocketController.addPlayer(message);
        assertEquals(expectedState, result);
        verify(gameService).addPlayer("Player1");
    }
}