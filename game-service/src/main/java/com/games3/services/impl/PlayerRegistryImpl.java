package com.games3.services.impl;

import com.games3.domain.Player;
import com.games3.domain.PlayerRegistry;
import com.games3.domain.repo.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerRegistryImpl implements PlayerRegistry {

    private final PlayerRepository playerRepository;

    @Override
    public void register(String playerId) {
        getOrCreatePlayer(playerId);
        log.info("player presence registered. playerId: {}", playerId);
    }

    public Optional<Player> getPlayer(String playerId) {
        return playerRepository.find(playerId);
    }

    private Player getOrCreatePlayer(String playerId) {
        return playerRepository.find(playerId).orElse(createAndSavePlayer(playerId));
    }

    private Player createAndSavePlayer(String playerId) {
        val player = new Player(playerId);
        return playerRepository.save(player);
    }
}
