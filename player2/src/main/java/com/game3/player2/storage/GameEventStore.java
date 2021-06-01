package com.game3.player1.storage;

import com.game3.player1.data.GameEvent;
import com.game3.player1.data.GameToPlay;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GameEventStore {

    private Queue<GameEvent> gameEvents = new LinkedList<>();

    private Map<UUID, Deque<GameToPlay.GameMovement>> gamesMap = new HashMap<>();

    public void add(GameEvent event) {

        val movements = gamesMap.computeIfAbsent(event.getGameId(), k -> new LinkedList<>());
        val movement = new GameToPlay.GameMovement();
        movement.setPlayerId(event.getRecentPlayerId());
        movement.setValue(event.getMovementValue());
        movement.setAddedValue(event.getAddedValue());
        movements.push(movement);

        gameEvents.add(event);
    }

    public void addGamesToPlay(List<GameToPlay> games) {

        for(val game: games) {
            val movements = gamesMap.computeIfAbsent(game.getGameId(), k -> new LinkedList<>());
            movements.addAll(game.getMovements());
        }
    }

    public Map<UUID, Deque<GameToPlay.GameMovement>> getAllGamesView() {
        return gamesMap; //
    }
}
