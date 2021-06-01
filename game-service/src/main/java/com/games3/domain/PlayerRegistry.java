package com.games3.domain;

import java.util.Optional;

public interface PlayerRegistry {

    void register(String playerId);

    Optional<Player> getPlayer(String playerId);

}
