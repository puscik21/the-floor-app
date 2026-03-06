package com.thefloorapp.game.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: rethink idea of multiple game IDs
/**
 * Persisted game state. There is always exactly one row (id = 1).
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameState {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private GameStateValue state;
}

