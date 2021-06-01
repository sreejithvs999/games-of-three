package com.game3.player1.commands;

import com.game3.player1.console.Platform;

import java.util.Objects;

public class NewGameAction implements Action {

    @Override
    public void execute(ActionContext ctx) throws Exception {

        Platform.echo("Enter starting game value: ");
        Integer value = getNumberSafe(Platform.readLine());

        if(Objects.isNull(value)) {
            Platform.echo("Wrong game value.");
            return;
        }

        ctx.getPlayerServices().startNewGame(value);
        Platform.echo("New game started...");
        Platform.pauseScreen(3);
    }

    static Integer getNumberSafe(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }
}
