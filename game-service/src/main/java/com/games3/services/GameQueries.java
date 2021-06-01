package com.games3.services;

import com.games3.domain.Game;
import com.games3.domain.Player;
import com.games3.domain.repo.GameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class GameQueries {

    private final GameRepository gameRepository;

    public List<Game> getActiveGamesWherePlayerHasToMove(String playerId) {
        val player = new Player(playerId);
        return gameRepository.findAll()
                .stream()
                .filter(this::isActive)
                .filter(new PlayerFilter(player).negate())
                .collect(Collectors.toList());
    }

    private boolean isActive(Game game) {
        return Boolean.FALSE.equals(game.isOver());
    }

    private class PlayerFilter implements Predicate<Game> {

        Player player;

        PlayerFilter(Player player) {
            this.player = player;
        }

        @Override
        public boolean test(Game game) {
            return game.getLastMovement().isPlayedBy(player);
        }
    }

}
