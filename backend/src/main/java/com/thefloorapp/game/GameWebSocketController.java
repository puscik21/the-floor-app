package com.thefloorapp.game;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Handles real-time game actions over WebSocket (STOMP).
 *
 * <p>Clients send messages to {@code /app/game.*} destinations and
 * all subscribers of {@code /topic/game} receive the broadcasts.</p>
 */
@Controller
public class GameWebSocketController {

    @MessageMapping("/game.start")
    @SendTo("/topic/game")
    public GameStartedEvent handleGameStart(StartGameRequest request) {
        String triggeredBy = request.playerName() != null ? request.playerName() : "Narrator";
        return new GameStartedEvent("Game has started!", triggeredBy);
    }
}
