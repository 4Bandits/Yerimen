package com.yerimen.screens.game.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.LinkedList;
import java.util.Queue;

public class NotificationManager {

    private Queue<String> notifications;
    private Boolean isShowingNotification;
    private Notification currentNotification;

    public NotificationManager() {
        this.notifications = new LinkedList<>();
        this.isShowingNotification = false;
    }

    public void render(SpriteBatch spriteBatch) {
        if(this.canRenderNotification()) {
            if(this.isShowingNotification) {
                if (this.currentNotification.isOnScreen()) {
                    this.currentNotification.render(spriteBatch);
                } else {
                    this.removeOlderNotification();
                    this.isShowingNotification = false;
                }
            } else {
                this.currentNotification = this.olderNotification();
                this.isShowingNotification = true;
            }
        }
    }

    public void showNotification(String newNotification) {
        this.notifications.add(newNotification);
    }

    private Boolean canRenderNotification() {
        return !this.notifications.isEmpty();
    }

    private Notification olderNotification() {
        return new Notification(this.notifications.peek());
    }

    private void removeOlderNotification() {
        this.notifications.remove();
    }

}
