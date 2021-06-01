package com.games3.domain;

import com.games3.domain.events.GameEventsPublisher;
import com.games3.domain.repo.GameRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class GameFactory {

    private final GameRepository gameRepository;
    private final PlayerRegistry playerRegistry;
    private final GameEventsPublisher gameEventsPublisher;

    public Game newGame() {

        Game game = new Game();
        game.init(UUID.randomUUID());
        setGameDependencies(game);
        return game;
    }

    public Game getGame(UUID gameId) {

        val game = gameRepository.find(gameId)
                .orElseThrow(throwGameNotFoundError(gameId));
        setGameDependencies(game);
        return game;
    }

    private void setGameDependencies(Game game) {
        game.setGameEventsPublisher(gameEventsPublisher);
        game.setGameRepository(gameRepository);
        game.setPlayerRegistry(playerRegistry);
    }

    private Supplier<GameException> throwGameNotFoundError(UUID gameId) {
        return () -> new GameException("No Game found. gameId: " + gameId);
    }
}
