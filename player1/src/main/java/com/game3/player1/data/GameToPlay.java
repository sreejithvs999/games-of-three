package com.game3.player1.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Data
@Setter
@Getter
public class GameToPlay {

    private UUID gameId;
    private List<GameMovement> movements;

    @Data
    @Getter
    @Setter
    public static class GameMovement {

        private String playerId;

        private Integer value;

        private Integer addedValue;
    }
}
