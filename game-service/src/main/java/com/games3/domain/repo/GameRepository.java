package com.games3.domain.repo;

import com.games3.domain.Game;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository {

    void save(Game game);

    Optional<Game> find(UUID id);

    Collection<Game> findAll();

    void deleteAll();
}
