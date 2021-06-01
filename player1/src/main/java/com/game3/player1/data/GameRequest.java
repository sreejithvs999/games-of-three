package com.game3.player1.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GameRequest {

    private String playerId;
    private int movementValue;

}
