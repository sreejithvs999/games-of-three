package com.games3.controller;

import com.games3.domain.GameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice("com.games3.controller")
public class GamesExceptionHandler {

    @ExceptionHandler(GameException.class)
    public ResponseEntity<Map<?, ?>> handle(GameException gameEx) {

        Map.of("error", gameEx.getMessage());
        return new ResponseEntity<>(Map.of("error", gameEx.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
