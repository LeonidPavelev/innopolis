package ru.innopolis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.innopolis.dto.GameMessageDto;
import ru.innopolis.dto.GameStateDto;
import ru.innopolis.service.GameService;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final GameService gameService;

    @MessageMapping("/game.sendMessage")
    @SendTo("/topic/gameState")
    public GameStateDto sendMessage(@Payload GameMessageDto message) {
        gameService.processGuess(message.getSender(), message.getContent());
        return gameService.getGameState();
    }

    @MessageMapping("/game.addPlayer")
    @SendTo("/topic/gameState")
    public GameStateDto addPlayer(@Payload GameMessageDto message) {
        gameService.addPlayer(message.getSender());
        return gameService.getGameState();
    }
}
