package com.games3.infra;

import com.games3.domain.Game;
import com.games3.domain.repo.GameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class DefaultGameRepository implements GameRepository {

    private final InMemoryGameStore inMemoryGameStore;

    @Override
    public void save(Game game) {
        inMemoryGameStore.saveOrReplace(game);
    }

    @Override
    public Optional<Game> find(UUID id) {
        return Optional.ofNullable(inMemoryGameStore.getGame(id));
    }

    @Override
    public Collection<Game> findAll() {
        return inMemoryGameStore.getAllGames();
    }

    @Override
    public void deleteAll() {
        inMemoryGameStore.deleteAllGames();
    }
}
