package com.game3.domain;

import com.game3.AbstractApplicationTests;
import com.games3.domain.GameException;
import com.games3.domain.GameFactory;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


public class GameTest extends AbstractApplicationTests {


    @Autowired
    private GameFactory gameFactory;


    @Test
    public void testGameFactory_getGame_with_invalid_gameId_throw_error() {

        UUID randomId = UUID.randomUUID();

        Assertions.assertThatThrownBy(() -> gameFactory.getGame(randomId))
                .isInstanceOf(GameException.class)
                .hasMessage("No Game found. gameId: %s", randomId);
    }
}
