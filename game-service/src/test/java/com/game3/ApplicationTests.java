package com.game3;

import com.games3.domain.Player;
import com.games3.domain.events.GameEvent;
import com.games3.domain.events.GameEventType;
import com.games3.services.GameRequest;
import com.games3.services.MovementRequest;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApplicationTests extends AbstractApplicationTests {


    @Before
    public void before() {
        gameRepository.deleteAll();
        playerRepository.deleteAll();
    }

    @Test
    public void testStartGame_createGameWithFirstMovement() throws Exception {

        //arrange
        val gameRequest = new GameRequest();
        gameRequest.setPlayerId("Player-1");
        gameRequest.setMovementValue(57);

        ArgumentCaptor<GameEvent> gameEventArgumentCaptor = ArgumentCaptor.forClass(GameEvent.class);
        Mockito.doNothing().when(gameEventsPublisher).fire(gameEventArgumentCaptor.capture());

        //act
        mvc.perform(put("/player-registry/{playerId}", "Player-1"))
                .andExpect(status().isOk());

        mvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonGameRequest.write(gameRequest).getJson()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        //assert
        val gamesCollection = gameRepository.findAll();
        Assertions.assertThat(gamesCollection.size()).isEqualTo(1);
        val savedGame = gamesCollection.stream().findFirst()
                .orElseGet(() -> Assertions.fail("no game elements"));

        Assertions.assertThat(savedGame.getId()).isNotNull();
        Assertions.assertThat(savedGame.getMovements()).isNotEmpty();
        val movement = savedGame.getLastMovement();
        Assertions.assertThat(movement).isNotNull();
        Assertions.assertThat(movement.getPlayer()).isEqualTo(new Player("Player-1"));
        Assertions.assertThat(movement.getValue()).isEqualTo(57);
        Assertions.assertThat(movement.getAddedValue()).isEqualTo(0);

        val gameEvent = gameEventArgumentCaptor.getValue();
        Assertions.assertThat(gameEvent.getGameId()).isEqualTo(savedGame.getId());
        Assertions.assertThat(gameEvent.getRecentPlayerId()).isEqualTo(movement.getPlayer().getId());
        Assertions.assertThat(gameEvent.getMovementValue()).isEqualTo(movement.getValue());
        Assertions.assertThat(gameEvent.getType()).isEqualTo(GameEventType.MOVEMENT);
    }


    @Test
    public void test_GameCreate_Playing_And_Finish__when_no_errors() throws Exception {

        ArgumentCaptor<GameEvent> gameEventArgumentCaptor = ArgumentCaptor.forClass(GameEvent.class);
        Mockito.doNothing().when(gameEventsPublisher).fire(gameEventArgumentCaptor.capture());

        val gameRequest = new GameRequest();
        gameRequest.setPlayerId("Player-1");
        gameRequest.setMovementValue(17);

        //player-1 register
        mvc.perform(put("/player-registry/{playerId}", "Player-1"));

        //player-1 start game
        mvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonGameRequest.write(gameRequest).getJson()));

        //player-2 register
        mvc.perform(put("/player-registry/{playerId}", "Player-2"));

        //player-2 get game with its turn
        var response = mvc.perform(get("/game/{playerId}/toMove", "Player-2"))
                .andReturn().getResponse().getContentAsString();
        var game = jsonListOfGameToPlay.parse(response).getObject().get(0);
        var lastMovement = game.getMovements().get(0);
        var newValue = (lastMovement.getValue() + 1) / 3; // from 17 to 6

        //player-2 make it's move with value 6
        var gameMove = new MovementRequest();
        gameMove.setPlayerId("Player-2");
        gameMove.setValue(newValue);
        mvc.perform(patch("/game/{gameId}", game.getGameId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMovementRequest.write(gameMove).getJson()))
                .andExpect(status().isOk());

        //player-1 get game with its turn
        response = mvc.perform(get("/game/{playerId}/toMove", "Player-1"))
                .andReturn().getResponse().getContentAsString();
        game = jsonListOfGameToPlay.parse(response).getObject().get(0);
        lastMovement = game.getMovements().get(0);
        newValue = (lastMovement.getValue() + 0) / 3; // from 6 to 2

        //player-1 make it's move with value 3
        gameMove = new MovementRequest();
        gameMove.setPlayerId("Player-1");
        gameMove.setValue(newValue);
        mvc.perform(patch("/game/{gameId}", game.getGameId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMovementRequest.write(gameMove).getJson()))
                .andExpect(status().isOk());

        //player-2 get game with its turn
        response = mvc.perform(get("/game/{playerId}/toMove", "Player-2"))
                .andReturn().getResponse().getContentAsString();
        game = jsonListOfGameToPlay.parse(response).getObject().get(0);
        lastMovement = game.getMovements().get(0);
        newValue = (lastMovement.getValue() + 1) / 3; // from 2 + 1 /3 to 1

        //player-2 make it's move with value 1
        gameMove = new MovementRequest();
        gameMove.setPlayerId("Player-2");
        gameMove.setValue(newValue);
        mvc.perform(patch("/game/{gameId}", game.getGameId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMovementRequest.write(gameMove).getJson()))
                .andExpect(status().isOk());


        val gameEvent = gameEventArgumentCaptor.getValue();
        Assertions.assertThat(gameEvent.getGameId()).isEqualTo(game.getGameId());
        Assertions.assertThat(gameEvent.getRecentPlayerId()).isEqualTo("Player-2");
        Assertions.assertThat(gameEvent.getMovementValue()).isEqualTo(1);
        Assertions.assertThat(gameEvent.getType()).isEqualTo(GameEventType.FINISHED);
    }
}
