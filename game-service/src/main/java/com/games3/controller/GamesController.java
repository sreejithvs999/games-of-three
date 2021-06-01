package com.games3.controller;

import com.games3.services.ApplicationService;
import com.games3.services.GameRequest;
import com.games3.services.GameToPlay;
import com.games3.services.MovementRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@Slf4j
@AllArgsConstructor
public class GamesController {

    private final ApplicationService applicationService;

    @RequestMapping(method = POST, path = "/game")
    public void startGame(@RequestBody GameRequest gameRequest) {
        applicationService.startGame(gameRequest);
    }

    @RequestMapping(method = PATCH, path = "/game/{gameId}")
    public void addMovement(@PathVariable UUID gameId, @RequestBody MovementRequest movementRequest) {
        movementRequest.setGameId(gameId);
        applicationService.addMovement(movementRequest);
    }

    @RequestMapping(method = GET, path = "/game/{playerId}/toMove")
    public List<GameToPlay> getGamesToPlay(@PathVariable String playerId) {
        return applicationService.getGamesToPlay(playerId);
    }
}
