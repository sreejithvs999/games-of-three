package com.games3.domain;

import com.games3.domain.events.GameEvent;
import com.games3.domain.events.GameEventsPublisher;
import com.games3.domain.repo.GameRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

import static com.games3.domain.events.GameEventType.FINISHED;
import static com.games3.domain.events.GameEventType.MOVEMENT;
import static java.lang.Boolean.FALSE;

@Getter
@Setter
public class Game {

    private static final int GAME_END_VALUE = 1;
    //persistable data
    private UUID id;
    private Deque<Movement> movements;
    //transient part
    private GameRepository gameRepository;
    private GameEventsPublisher gameEventsPublisher;
    private PlayerRegistry playerRegistry;

    public void init(UUID id) {
        assert Objects.nonNull(id);
        assert Objects.isNull(this.id);
        this.id = id;
        movements = new LinkedList<>();
    }

    public void addMovement(String playerId, int value) {

        val player = playerRegistry.getPlayer(playerId)
                .orElseThrow(throwPlayerNotFoundError(playerId));

        if (hasAnyPastMoves()) {
            validatePlayerChance(player);
            validateGameNotOver();
        }

        val movementValidator = MovementValidator.getInstance(value)
                .lastMovement(getLastMovement());

        if (movementValidator.isValid()) {
            val movement = Movement.builder()
                    .player(player)
                    .value(value)
                    .addedValue(movementValidator.getCalculatedAddedValue())
                    .build();
            movements.push(movement);
            fireGameEvent(movement);
        } else {
            throwMovementValueInvalidError();
        }
    }

    public void save() {
        this.gameRepository.save(this);
    }

    private boolean hasAnyPastMoves() {
        return FALSE.equals(movements.isEmpty());
    }

    public Movement getLastMovement() {
        return movements.peekFirst();
    }

    public boolean isOver() {
        return hasGameEndValue(getLastMovement());
    }

    private void fireGameEvent(Movement movement) {
        val event = GameEvent.builder()
                .type(hasGameEndValue(movement) ? FINISHED : MOVEMENT)
                .gameId(getId())
                .recentPlayerId(movement.getPlayer().getId())
                .movementValue(movement.getValue())
                .addedValue(movement.getAddedValue())
                .build();
        gameEventsPublisher.fire(event);
    }

    private boolean hasGameEndValue(Movement movement) {
        return movement.getValue() == GAME_END_VALUE;
    }

    private Supplier<GameException> throwPlayerNotFoundError(String playerId) {
        return () -> new GameException("Player yet to be registered. playerId: " + playerId);
    }

    private void validatePlayerChance(Player player) {
        if (getLastMovement().isPlayedBy(player)) {
            throw new GameException("Cannot add movement. last movement is from the same player.");
        }
    }

    private void validateGameNotOver() {
        if (isOver()) {
            throw new GameException("Game is over !.");
        }
    }

    private void throwMovementValueInvalidError() {
        throw new GameException(String.format(
                "Movement is not valid. It should be greater than 0 and added number should be within the range '%s'",
                MovementValidator.ADDED_VALUE_RANGE.toString()));
    }
}
