package com.games3.services;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class MovementRequest {

    private UUID gameId;
    private int value;
    private String playerId;
}
