package com.game3;

import com.games3.Application;
import com.games3.domain.events.GameEventsPublisher;
import com.games3.domain.repo.GameRepository;
import com.games3.domain.repo.PlayerRepository;
import com.games3.services.GameRequest;
import com.games3.services.GameToPlay;
import com.games3.services.MovementRequest;
import lombok.RequiredArgsConstructor;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@RequiredArgsConstructor
public abstract class AbstractApplicationTests {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected JacksonTester<GameRequest> jsonGameRequest;
    @Autowired
    protected JacksonTester<List<GameToPlay>> jsonListOfGameToPlay;
    @Autowired
    protected JacksonTester<MovementRequest> jsonMovementRequest;

    @Autowired
    protected GameRepository gameRepository;
    @Autowired
    protected PlayerRepository playerRepository;

    @MockBean
    protected GameEventsPublisher gameEventsPublisher;
}
