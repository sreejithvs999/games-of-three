package com.game3.player1.commands;

import com.game3.player1.console.Platform;
import lombok.val;

public class ShowGameAction implements Action {

    @Override
    public void execute(ActionContext ctx) throws Exception {


        Platform.clearScreen();
        val games = ctx.getGameEventStore().getAllGamesView();
        games.forEach((id, movements) -> {

            Platform.echo("------------------------------");
            Platform.echo("Game Id : " + id);
            Platform.echo("\tMovements");
            movements.forEach(movement -> {
                Platform.echo(String.format("\t\t %s => %4d  (%d)", movement.getPlayerId(), movement.getValue(), movement.getAddedValue()));
            });
        });
        if (games.isEmpty()) {
            Platform.echo("No games to list !. ");
        }
        Platform.echo("Enter something to return");
        Platform.readLine();
    }
}
