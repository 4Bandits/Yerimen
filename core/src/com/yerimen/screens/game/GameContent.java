package com.yerimen.screens.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.level.Level;
import com.yerimen.players.Character;
import com.yerimen.players.CharacterStatus;
import com.yerimen.players.Player;
import com.yerimen.powers.Power;
import com.yerimen.textures.TextureManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameContent {

    private Player mainPlayer;
    private Level level;
    private HashMap<String, Character> enemies;
    private List<Power> powers;


    public GameContent() {
        this.level = new Level(TextureManager.getInstance().getGrass());
        this.enemies = new HashMap<>();
        this.powers = new ArrayList<>();
    }

    public void update(float delta, OrthographicCamera camera) {
        mainPlayer.update(delta, camera);
        this.getEnemies().forEach(enemy -> enemy.update(delta, camera));
        this.getPowers().forEach(power -> power.update(delta));
    }

    public void render(SpriteBatch batch) {
        this.level.render(batch);
        this.getEnemies().forEach(player -> player.render(batch));
        this.getPowers().forEach(power -> power.render(batch));
        mainPlayer.render(batch);
    }

    public void setMainPlayer(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    public Player getMainPlayer() {
        return this.mainPlayer;
    }

    public void addEnemy(String enemyId) {
        enemies.put(enemyId, new Character(TextureManager.getInstance().getWerewolfTexture(), new CharacterStatus(), new Vector2(0, 0)));
    }

    public void addEnemy(String enemyId, Vector2 position) {
        enemies.put(enemyId, new Character(TextureManager.getInstance().getWerewolfTexture(), new CharacterStatus(), position));
    }

    public void removeEnemy(String enemyId) {
        enemies.remove(enemyId);
    }

    public void moveEnemy(String enemyId, float x, float y, String direction) {
        Character enemy = enemies.get(enemyId);
        if (enemy != null)
            enemy.move(x, y).setDirection(direction).setMoving(true);
    }

    public List<Character> getEnemies() {
        return new ArrayList<>(this.enemies.values());
    }

    public void addPower(Power power) {
        this.powers.add(power);
    }

    public List<Power> getPowers() {
        return new ArrayList<>(this.powers);
    }
}