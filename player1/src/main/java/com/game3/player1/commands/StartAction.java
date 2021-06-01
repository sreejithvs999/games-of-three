package com.game3.player1.commands;

import com.game3.player1.console.Platform;

public class StartAction implements Action {

    @Override
    public void execute(ActionContext ctx) throws Exception{

        Platform.clearScreen();
        Platform.echo("1. Start New Game");
        Platform.echo("2. Show Games");
        Platform.echo("3. Exit");
        String option = Platform.readLine();

        switch (option) {
            case "1" :
                ctx.push(this);
                ctx.push(new NewGameAction());
                break;

            case "2":
                ctx.push(this);
                ctx.push(new ShowGameAction());
                break;

            case "3":
                Platform.echo("Exiting...");
                return;

            default:
                Platform.echo("Wrong option...");
                ctx.push(this); //repeat the same
        }
        Platform.pauseScreen(1);
    }
}
