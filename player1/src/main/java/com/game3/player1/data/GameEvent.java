package com.game3.player1.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class GameEvent {

    private GameEventType type;

    private UUID gameId;

    private String recentPlayerId;

    private Integer movementValue;

    private Integer addedValue;
}
