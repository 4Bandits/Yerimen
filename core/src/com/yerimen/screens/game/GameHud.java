package com.yerimen.screens.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yerimen.screens.game.hud.Notification;

public class GameHud {

    private Notification newPlayerNotification;
    private Notification enemyKilledNotification;

    public GameHud() {
        this.newPlayerNotification = new Notification("You just joined the game!");
        this.enemyKilledNotification = new Notification("You just killed an enemy!");
    }

    public void render(SpriteBatch spriteBatch) {
        /* Example usage */
        this.newPlayerNotification.render(spriteBatch);

        if(!this.newPlayerNotification.isOnScreen()) {
            this.enemyKilledNotification.render(spriteBatch);
        }
    }

}
