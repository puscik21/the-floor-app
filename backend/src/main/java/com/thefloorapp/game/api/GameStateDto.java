package com.thefloorapp.game.api;

import com.thefloorapp.game.domain.GameState;
import com.thefloorapp.game.domain.GameStateValue;

public record GameStateDto(GameStateValue state) {

    public static GameStateDto from(GameState entity) {
        return new GameStateDto(entity.getState());
    }
}

