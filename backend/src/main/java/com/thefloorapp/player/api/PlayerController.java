package com.thefloorapp.player.api;

import com.thefloorapp.player.domain.Player;
import com.thefloorapp.player.domain.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public List<Player> findPlayers(@RequestParam(defaultValue = "true") boolean isPlaying) {
        return playerService.findPlayers(isPlaying);
    }
}
