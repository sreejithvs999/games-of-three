package com.game3.player1.console;

import com.game3.player1.commands.ActionContext;
import com.game3.player1.commands.StartAction;
import com.game3.player1.data.GameEvent;
import com.game3.player1.services.PlayerServices;
import com.game3.player1.storage.GameEventStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PlayerConsole implements InitializingBean {
    private static final String TOPIC_GAME_EVENTS = "games3events.topic";

    private final PlayerServices playerServices;
    private final GameEventStore gameEventStore;

    private ActionContext actionContext;

    public void run(ApplicationArguments args) throws Exception {

        try {
            playerServices.registerPlayer();
            playerServices.playAnyMovementsPending();

            while (actionContext.hashMoreActions()) {

                actionContext.pop().execute(actionContext);
            }

        } catch (Exception ie) {
            Thread.currentThread().interrupt();
            log.error("exit playerConsole", ie);
        }
    }

    @JmsListener(destination = TOPIC_GAME_EVENTS)
    public void receiveGameEvents(GameEvent gameEvent) {
        log.info("received game event: {} ", gameEvent);
        gameEventStore.add(gameEvent);

        playerServices.handleGameEvent(gameEvent);
    }

    @Override
    public void afterPropertiesSet() {

        actionContext = new ActionContext(playerServices, gameEventStore);
        actionContext.push(new StartAction());
    }
}
