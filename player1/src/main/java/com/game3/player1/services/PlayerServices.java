package com.game3.player1.services;

import com.game3.player1.console.Platform;
import com.game3.player1.data.GameEvent;
import com.game3.player1.data.GameEventType;
import com.game3.player1.data.GameToPlay;
import com.game3.player1.data.MovementRequest;
import com.game3.player1.storage.GameEventStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerServices {

    private final GameServiceClient gameServiceClient;
    private final GameEventStore gameEventStore;

    private static final String playerId = "Player-1";
    private static final int GAME_VALUE = 3;

    public void registerPlayer() {
        gameServiceClient.callRegisterPlayer(playerId);
        Platform.echo("************  " + playerId + " is ready !");
    }

    public void startNewGame(Integer gameValue) {
        gameServiceClient.callNewGame(gameValue, playerId);
    }

    public void playAnyMovementsPending() {
        val games = gameServiceClient.callGetGamesToPlay(playerId);
        gameEventStore.addGamesToPlay(games);
        for (GameToPlay game : games) {

            game.getMovements().stream().findFirst().ifPresent(gameMovement -> {
                val movementRequest = MovementRequest.builder()
                        .gameId(game.getGameId())
                        .playerId(playerId)
                        .value(nextMovementValue(gameMovement.getValue()))
                        .build();
                log.info("Making movement: {}", movementRequest);
                gameServiceClient.callAddMovement(movementRequest);
            });
        }
    }


    public void handleGameEvent(GameEvent event) {
        resolveMovement(event).ifPresent(gameServiceClient::callAddMovement);
    }


    private Optional<MovementRequest> resolveMovement(GameEvent event) {

        if (shouldPlay(event)) {
            log.info("Decided to play on {}", event);
            val movementRequest = MovementRequest.builder()
                    .gameId(event.getGameId())
                    .playerId(playerId)
                    .value(nextMovementValue(event.getMovementValue()))
                    .build();
            log.info("Making movement: {}", movementRequest);

            return Optional.of(movementRequest);
        }
        return Optional.empty();
    }

    private int nextMovementValue(int value) {
        val remainder = value % GAME_VALUE;
        int newValue;
        if (remainder <= GAME_VALUE / 2) {
            newValue = (value - remainder) / GAME_VALUE;
        } else {
            newValue = (value + remainder) / GAME_VALUE;
        }
        return newValue;
    }

    private boolean shouldPlay(GameEvent event) {
        return !playerId.equals(event.getRecentPlayerId()) //movement from opponent
                && event.getType() != GameEventType.FINISHED; //game not finished yet
    }
}
