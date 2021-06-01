package com.games3.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Builder
@Data
public class Movement {

    private Player player;

    private Integer value;

    private Integer addedValue;

    public boolean isPlayedBy(Player player) {

        assert Objects.nonNull(player);
        return this.player.equals(player);
    }

}
