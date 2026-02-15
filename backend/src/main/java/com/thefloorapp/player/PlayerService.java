package com.thefloorapp.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public List<Player> findPlayers(boolean isPlaying) {
        return playerRepository.findAllByIsPlaying(isPlaying);
    }
}
