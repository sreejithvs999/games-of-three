package com.games3.domain.repo;

import com.games3.domain.Player;

import java.util.Collection;
import java.util.Optional;

public interface PlayerRepository {

    Player save(Player player);

    Optional<Player> find(String id);

    Collection<Player> findAll();

    void deleteAll();
}
