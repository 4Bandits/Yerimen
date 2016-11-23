package com.yerimen.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.players.Character;
import com.yerimen.players.Player;
import com.yerimen.screens.ScreenManager;
import com.yerimen.screens.YerimenScreen;
import com.yerimen.server.Server;

import java.util.HashMap;

public class GameScreen extends YerimenScreen {

    private GameContent gameContent;
    private GameHud gameHud;

    public GameScreen(ScreenManager gsm, Server server, Player player, HashMap<String, Character> enemies) {
        super(gsm);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        this.gameContent = new GameContent(player, server, enemies);
        this.gameHud = new GameHud();

        this.initializeCamera();
    }

    private void initializeCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
    }

    @Override
    protected void handleInput() {

    }

    public void update(float delta) {
        this.gameContent.update(delta, camera);
        this.updateCamera();
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.updateCamera();
        sb.setProjectionMatrix(camera.combined);
        this.draw(sb, sr);
    }

    @Override
    public void dispose() {

    }

    private void draw(SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        this.gameContent.render(sb, sr);
        this.gameHud.render(sb);
        sb.end();
    }

    private void updateCamera() {
        Vector2 vector2 = this.gameContent.getMainPlayer().getPosition();
        float x = Math.min(Math.max(vector2.x, 960), 2240);
        float y = Math.min(Math.max(vector2.y, 550), 2670);
        camera.position.set(x, y, 0);
        camera.update();
    }
}
