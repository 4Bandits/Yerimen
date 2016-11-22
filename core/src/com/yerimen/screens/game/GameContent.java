package com.yerimen.screens.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.level.Level;
import com.yerimen.level.MiniMap;
import com.yerimen.players.Character;
import com.yerimen.players.CharacterStatus;
import com.yerimen.players.Player;
import com.yerimen.players.PlayerFactory;
import com.yerimen.powers.Power;
import com.yerimen.server.Server;
import com.yerimen.textures.TextureManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameContent {

    private Server server;
    private Player mainPlayer;
    private Level level;
    private HashMap<String, Character> enemies;
    private List<Power> powers;
    private MiniMap miniMap;

    public GameContent(Player player) {
        this.mainPlayer = player;
        this.level = new Level(TextureManager.getInstance().getGrass());
        this.enemies = new HashMap<>();
        this.powers = new ArrayList<>();
        this.miniMap = new MiniMap();
    }

    public void update(float delta, OrthographicCamera camera) {
        mainPlayer.update(delta, camera);
        this.getEnemies().forEach(enemy -> enemy.update(delta, camera));
        this.getPowers().forEach(power -> power.update(delta, this.getAllPlayers(), this));
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.level.render(batch);

        this.getEnemies().forEach(player -> player.render(batch, shapeRenderer, Color.RED));
        this.getPowers().forEach(power -> power.render(batch));
        mainPlayer.render(batch, shapeRenderer, Color.BLUE);
        this.miniMap.render(batch,shapeRenderer);
    }

    public Player getMainPlayer() {
        return this.mainPlayer;
    }

    public void addEnemy(String enemyId, Vector2 position, String characterSelected) {
        enemies.put(enemyId, PlayerFactory.getCharacter(enemyId,position,characterSelected));
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

    public void removePower(Power power) {
        this.server.destroyPower(power);
        this.powers.remove(power);
    }

    private List<Character> getAllPlayers(){
        List<Character> allPlayers = this.getEnemies();
        allPlayers.add(this.mainPlayer);
        return allPlayers;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}