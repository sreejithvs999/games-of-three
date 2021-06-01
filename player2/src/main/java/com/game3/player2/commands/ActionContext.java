package com.game3.player1.commands;

import com.game3.player1.services.PlayerServices;
import com.game3.player1.storage.GameEventStore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@RequiredArgsConstructor
public class ActionContext {

    @Getter
    private final PlayerServices playerServices;
    @Getter
    private final GameEventStore gameEventStore;

    private Map<String, Object> state = new HashMap<>();
    private Deque<Action> actions = new LinkedList<>();

    public void set(String key, Object value) {
        state.put(key, value);
    }

    public String getString(String key) {
        var value = state.get(key);
        return value instanceof String ? (String) value : null;
    }

    public long getInteger(String key) {
        var value = state.get(key);
        return value instanceof Integer ? (Integer) value : null;
    }

    public void push(Action action) {
        actions.push(action);
    }

    public Action pop() {
        return actions.pop();
    }

    public boolean hashMoreActions() {
        return !actions.isEmpty();
    }
}
