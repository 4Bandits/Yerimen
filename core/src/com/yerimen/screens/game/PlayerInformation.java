package com.yerimen.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.bars.HealthBar;
import com.yerimen.players.Player;

public class PlayerInformation {

    private Player player;
    private Vector2 position;
    private BitmapFont playerNameLabel;
    private BitmapFont playerHpLabel;
    private HealthBar healthBar;

    public PlayerInformation(Player player) {
        this.player = player;
        this.position = new Vector2(20, this.topScreen());

        this.initializePlayerNameLabel();
        this.initializePlayerHPLabel();

        this.healthBar = new HealthBar(this.position.x, this.position.y, 30, this.playerHP());
    }

    public void render(SpriteBatch spriteBatch) {
        this.healthBar.update(this.playerHP(), this.position.x, this.position.y);

        this.playerNameLabel.draw(spriteBatch, this.player.getName(), this.position.x + 8, this.position.y - 10);
        this.healthBar.render(spriteBatch);
        this.playerHpLabel.draw(spriteBatch, this.hpText(), this.position.x + 8, this.position.y + 30);
    }

    private void initializePlayerHPLabel() {
        this.playerHpLabel = new BitmapFont();
        this.playerHpLabel.getData().setScale(1.5f);
        this.playerHpLabel.setColor(1, 1, 1, 0.5f);
    }

    private void initializePlayerNameLabel() {
        this.playerNameLabel = new BitmapFont();
        this.playerNameLabel.getData().setScale(2f);
        this.playerNameLabel.setColor(1, 1, 1, 1);
    }

    private float topScreen() {
        return Gdx.graphics.getHeight() - 50;
    }

    private String hpText() {
        return "HP: " + String.valueOf(this.player.getHp());
    }

    private int playerHP() {
        return Double.valueOf(this.player.getHp() * 1.8).intValue();
    }

}
