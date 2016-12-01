package com.yerimen.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.players.Character;
import com.yerimen.players.Player;
import com.yerimen.screens.ScreenManager;
import com.yerimen.screens.YerimenScreen;
import com.yerimen.screens.score.ScoreScreen;
import com.yerimen.server.Server;

import java.util.HashMap;

public class GameScreen extends YerimenScreen {

    private GameContent gameContent;
    private GameHud gameHud;

    public GameScreen(ScreenManager gameScreenManager, Server server, Player player, HashMap<String, Character> enemies) {
        super(gameScreenManager);
        this.gameContent = new GameContent(player, server, enemies);
        this.gameContent.registerUsername(server.getUserInformation().getUsername());
        this.gameHud = new GameHud();

        this.initializeCamera();
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.TAB)){
            gsm.push(new ScoreScreen(gameContent,gsm));
        }

    }

    public void update(float delta) {
        this.gameContent.update(delta, camera);
        this.updateCamera();
        this.handleInput();
    }


    @Override
    public void render(SpriteBatch spriteBatch, SpriteBatch hudBatch, ShapeRenderer shapeRenderer) {
        this.updateCamera();
        spriteBatch.setProjectionMatrix(camera.combined);
        this.draw(spriteBatch, hudBatch, shapeRenderer);
    }

    @Override
    public void dispose() {

    }

    private void initializeCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
    }

    private void draw(SpriteBatch spriteBatch, SpriteBatch hudBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        this.gameContent.render(spriteBatch, shapeRenderer);
        spriteBatch.end();

        hudBatch.begin();
        this.gameHud.render(hudBatch);
        hudBatch.end();
    }

    private void updateCamera() {
        Vector2 vector2 = this.gameContent.getMainPlayer().getPosition();
        float x = Math.min(Math.max(vector2.x, 960), 2240);
        float y = Math.min(Math.max(vector2.y, 550), 2670);
        camera.position.set(x, y, 0);
        camera.update();
    }
}
