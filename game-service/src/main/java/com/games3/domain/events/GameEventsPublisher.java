package com.games3.domain.events;

public interface GameEventsPublisher {

    void fire(GameEvent event);
}
