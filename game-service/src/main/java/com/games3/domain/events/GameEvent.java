package com.games3.domain.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class GameEvent {

    private GameEventType type;

    private UUID gameId;

    private String recentPlayerId;

    private Integer movementValue;

    private Integer addedValue;
}
