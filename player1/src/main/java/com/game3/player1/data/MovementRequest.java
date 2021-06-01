package com.game3.player1.data;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
@Builder
public class MovementRequest {

    private UUID gameId;
    private int value;
    private String playerId;
}
