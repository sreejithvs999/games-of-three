package com.games3.domain;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.Objects;

@Slf4j
public class MovementValidator {

    public static final int GAME_NUMBER = 3;
    public static final List<Integer> ADDED_VALUE_RANGE = List.of(-1, 0, 1);
    private Movement lastMovement;
    private int movedValue;
    private int calculatedAddedValue;

    private MovementValidator(int value) {
        movedValue = value;
    }

    public static MovementValidator getInstance(int movedValue) {
        return new MovementValidator(movedValue);
    }

    public MovementValidator lastMovement(Movement movement) {

        lastMovement = movement;
        if (Objects.nonNull(lastMovement)) {
            val lastValue = lastMovement.getValue();
            calculatedAddedValue = movedValue * GAME_NUMBER - lastValue;
        }
        return this;
    }

    public boolean isValid() {

        return movedValue > 0
                && ADDED_VALUE_RANGE.contains(calculatedAddedValue);
    }

    public int getCalculatedAddedValue() {
        return calculatedAddedValue;
    }
}
