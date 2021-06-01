package com.games3.controller;

import com.games3.services.ApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class PlayerController {

    private final ApplicationService applicationService;

    @RequestMapping(method = RequestMethod.PUT, path = "/player-registry/{playerId}")
    public void updatePlayerPresence(@PathVariable String playerId) {
        applicationService.updatePlayerPresence(playerId);
    }

}



