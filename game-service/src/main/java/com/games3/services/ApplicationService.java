package com.games3.services;

import com.games3.domain.GameFactory;
import com.games3.domain.PlayerRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ApplicationService {

    private final PlayerRegistry playerRegistry;
    private final GameFactory gameFactory;
    private final GameQueries gameQueries;

    public void updatePlayerPresence(String playerId) {
        playerRegistry.register(playerId);
    }

    public void startGame(GameRequest gameRequest) {

        val game = gameFactory.newGame();
        game.addMovement(gameRequest.getPlayerId(), gameRequest.getMovementValue());
        game.save();
    }

    public void addMovement(MovementRequest movementRequest) {

        val game = gameFactory.getGame(movementRequest.getGameId());
        game.addMovement(movementRequest.getPlayerId(), movementRequest.getValue());
        game.save();
    }

    public List<GameToPlay> getGamesToPlay(String playerId) {
        return gameQueries.getActiveGamesWherePlayerHasToMove(playerId)
                .stream()
                .map(GameToPlay::fromGame)
                .collect(Collectors.toList());
    }

}
