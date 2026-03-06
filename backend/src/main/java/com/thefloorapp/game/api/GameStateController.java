package com.thefloorapp.game.api;

import com.thefloorapp.game.domain.GameStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameStateController {

    private final GameStateService gameStateService;

    @GetMapping("/state")
    public ResponseEntity<GameStateDto> getGameState() {
        return ResponseEntity.ok(gameStateService.getGameState());
    }

    @PutMapping("/state")
    public ResponseEntity<GameStateDto> updateGameState(@RequestBody GameStateDto request) {
        return ResponseEntity.ok(gameStateService.updateGameState(request.state()));
    }
}

