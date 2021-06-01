package com.games3.infra;

import com.games3.domain.events.GameEvent;
import com.games3.domain.events.GameEventsPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class GameEventsPublisherImpl implements GameEventsPublisher, InitializingBean {

    private static final String TOPIC_GAME_EVENTS = "games3events.topic";
    private final JmsTemplate jmsTemplate;

    @Override
    public void fire(GameEvent event) {

        jmsTemplate.convertAndSend(TOPIC_GAME_EVENTS, event);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jmsTemplate.setPubSubDomain(true);
    }
}
