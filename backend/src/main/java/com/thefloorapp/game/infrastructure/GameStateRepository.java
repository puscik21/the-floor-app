package com.thefloorapp.game.infrastructure;

import com.thefloorapp.game.domain.GameState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStateRepository extends JpaRepository<GameState, Long> {
}

