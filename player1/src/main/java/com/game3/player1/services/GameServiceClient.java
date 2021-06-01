package com.game3.player1.services;

import com.game3.player1.data.GameRequest;
import com.game3.player1.data.GameToPlay;
import com.game3.player1.data.MovementRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
@Slf4j
public class GameServiceClient implements InitializingBean {

    private static final String baseUri = "http://localhost:8080";
    private static final int TIMEOUT = 10000;

    RestTemplate restTemplate = new RestTemplate();

    public void callRegisterPlayer(String playerId) {

        restTemplate.put(baseUri + "/player-registry/{playerId}", null, playerId);
    }

    public void callNewGame(Integer gameValue, String playerId) {

        val gameRequest = new GameRequest();
        gameRequest.setMovementValue(gameValue);
        gameRequest.setPlayerId(playerId);
        restTemplate.postForLocation(baseUri + "/game", gameRequest);
    }

    public List<GameToPlay> callGetGamesToPlay(String playerId) {

        return List.of(restTemplate.getForObject(baseUri + "/game/{playerId}/toMove", GameToPlay[].class, playerId));
    }

    public void callAddMovement(MovementRequest request) {

        try {
            restTemplate.patchForObject(baseUri + "/game/{gameId}", request, Object.class, request.getGameId());
        } catch (HttpClientErrorException badRequest) {
            log.warn("got bad request. {}", badRequest.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(TIMEOUT);
        requestFactory.setReadTimeout(TIMEOUT);

        restTemplate.setRequestFactory(requestFactory);
    }
}
