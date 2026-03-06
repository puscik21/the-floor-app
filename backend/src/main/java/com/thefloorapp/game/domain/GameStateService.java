package com.thefloorapp.game.domain;

import com.thefloorapp.game.api.GameStateDto;
import com.thefloorapp.game.infrastructure.GameStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameStateService {

    static final long GAME_STATE_ID = 1L;
    static final String TOPIC_GAME_STATE = "/topic/game-state";

    private final GameStateRepository gameStateRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public GameStateDto getGameState() {
        return GameStateDto.from(loadOrInit());
    }

    public GameStateDto updateGameState(GameStateValue newState) {
        GameState gameState = loadOrInit();
        gameState.setState(newState);
        gameStateRepository.save(gameState);

        GameStateDto dto = GameStateDto.from(gameState);
        messagingTemplate.convertAndSend(TOPIC_GAME_STATE, dto);
        return dto;
    }

    private GameState loadOrInit() {
        return gameStateRepository.findById(GAME_STATE_ID)
            .orElseGet(() -> gameStateRepository.save(new GameState(GAME_STATE_ID, GameStateValue.INIT)));
    }
}

