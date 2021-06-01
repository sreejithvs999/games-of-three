package com.games3.services;

import com.games3.domain.Game;
import com.games3.domain.Movement;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Setter
@Getter
public class GameToPlay {

    private UUID gameId;
    private List<GameMovement> movements;


    public static GameToPlay fromGame(Game game) {
        val gameToPlay = new GameToPlay();
        gameToPlay.setGameId(game.getId());
        gameToPlay.setMovements(
                game.getMovements().stream()
                        .map(GameMovement::fromMovement)
                        .collect(Collectors.toList())
        );
        return gameToPlay;
    }

    @Data
    @Getter
    @Setter
    public static class GameMovement {

        private String playerId;

        private Integer value;

        private Integer addedValue;

        public static GameMovement fromMovement(Movement movement) {
            val gameMovement = new GameMovement();
            gameMovement.setValue(movement.getValue());
            gameMovement.setPlayerId(movement.getPlayer().getId());
            gameMovement.setAddedValue(movement.getAddedValue());
            return gameMovement;
        }
    }
}
