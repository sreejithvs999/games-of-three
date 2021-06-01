package com.games3.infra;

import com.games3.domain.Game;
import com.games3.domain.Player;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class InMemoryGameStore {

    private Map<UUID, Game> idToGameMap = new HashMap<>();
    private Map<String, Player> idToPlayerMap = new HashMap<>();

    public Game getGame(UUID id) {
        return idToGameMap.get(id);
    }

    public Game saveOrReplace(Game game) {
        return idToGameMap.put(game.getId(), game);
    }

    public Collection<Game> getAllGames() {
        return idToGameMap.values();
    }

    public void deleteAllGames() {
        idToGameMap.clear();
    }

    public Player getPlayer(String id) {
        return idToPlayerMap.get(id);
    }

    public Player saveOrReplace(Player player) {
        return idToPlayerMap.put(player.getId(), player);
    }

    public Collection<Player> getAllPlayers() {
        return idToPlayerMap.values();
    }

    public void deleteAllPlayers() {
        idToPlayerMap.clear();
    }
}
