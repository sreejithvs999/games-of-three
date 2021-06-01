package com.game3.player1.services;

import com.game3.player1.data.GameEvent;
import com.game3.player1.data.GameEventType;
import com.game3.player1.data.MovementRequest;
import com.game3.player1.storage.GameEventStore;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServicesTest {

    @Mock
    private GameServiceClient gameServiceClient;

    @InjectMocks
    private PlayerServices playerServices;

    @Test
    public void test_registerPlayer_invoke_callRegisterPlayer_on_gameServiceClient() {

        //arrange
        ArgumentCaptor<String> registerPlayerArgsCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.doNothing().when(gameServiceClient).callRegisterPlayer(registerPlayerArgsCaptor.capture());

        //act
        playerServices.registerPlayer();

        //assert
        Assertions.assertThat(registerPlayerArgsCaptor.getValue()).isEqualTo("Player-1");
    }

    @Test
    public void test_handleGameEvent_create_next_movement() {

        //arrange
        ArgumentCaptor<MovementRequest> captor = ArgumentCaptor.forClass(MovementRequest.class);
        Mockito.doNothing().when(gameServiceClient).callAddMovement(captor.capture());

        //act
        val event = new GameEvent();
        event.setGameId(UUID.randomUUID());
        event.setMovementValue(10);
        event.setAddedValue(1);
        event.setRecentPlayerId("Another-Player-Id");
        event.setType(GameEventType.MOVEMENT);
        playerServices.handleGameEvent(event);

        //assert
        val movementRequest = captor.getValue();
        Mockito.verify(gameServiceClient).callAddMovement(movementRequest);
        Assertions.assertThat(movementRequest.getGameId()).isEqualTo(event.getGameId());
        Assertions.assertThat(movementRequest.getPlayerId()).isEqualTo("Player-1");
        Assertions.assertThat(movementRequest.getValue()).isEqualTo(3); // 10 -1  /3

    }
}
