package com.games3.infra;

import com.games3.domain.Player;
import com.games3.domain.repo.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class DefaultPlayerRepository implements PlayerRepository {

    private final InMemoryGameStore inMemoryGameStore;

    @Override
    public Player save(Player player) {
        return inMemoryGameStore.saveOrReplace(player);
    }

    @Override
    public Optional<Player> find(String id) {
        return Optional.ofNullable(inMemoryGameStore.getPlayer(id));
    }

    @Override
    public Collection<Player> findAll() {
        return inMemoryGameStore.getAllPlayers();
    }

    @Override
    public void deleteAll() {
        inMemoryGameStore.deleteAllPlayers();
    }
}
