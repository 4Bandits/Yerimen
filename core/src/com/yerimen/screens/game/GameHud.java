package com.yerimen.screens.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yerimen.players.Player;
import com.yerimen.powers.Power;
import com.yerimen.screens.game.hud.NotificationManager;
import com.yerimen.screens.game.hud.SkillBar;
import com.yerimen.server.Observer;
import com.yerimen.server.Server;
import org.json.JSONObject;

public class GameHud implements Observer {

    private NotificationManager notificationManager;
    private SkillBar skillBar;

    public GameHud(Player player, Server server) {
        this.notificationManager = new NotificationManager();
        this.skillBar = new SkillBar();

        player.addObserver(this);
        server.setGameHud(this);
    }

    public void render(SpriteBatch spriteBatch) {
        this.notificationManager.render(spriteBatch);
        this.skillBar.render(spriteBatch);
    }

    public void showNotification(String notification) {
        this.notificationManager.showNotification(notification);
    }

    @Override
    public void update(JSONObject jsonObject) {

    }

    @Override
    public void update(Power power) {

    }

    @Override
    public void updateKill() {
        this.showNotification("You were killed.");
    }

    @Override
    public void updateSkillChanged(String newSkill) {
        this.skillBar.changeSkill(newSkill);
    }

}
