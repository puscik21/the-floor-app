package com.thefloorapp.game.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Locale;

public enum GameStateValue {
    INIT,
    FLOOR,
    READY,
    DUEL,
    FINISHED,
    PODIUM;

    @JsonValue
    public String getValue() {
        return name().toLowerCase(Locale.ROOT);
    }
}

