package com.thefloorapp.player.infrastructure;

import com.thefloorapp.player.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findAllByIsPlaying(boolean isPlaying);
}
