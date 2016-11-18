package com.yerimen.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.YerimenGame;

public class GameScreen extends ScreenAdapter {

    private YerimenGame game;
    private GameContent gameContent;
    private GameHud gameHud;
    private OrthographicCamera camera;

    public GameScreen(YerimenGame game) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        this.game = game;
        this.gameContent = new GameContent();
        this.gameHud = new GameHud();
        this.game.connect(this.gameContent);

        this.initializeCamera();
    }

    private void initializeCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.updateCamera();
        this.game.setProjectionMatrix(camera.combined);
        this.update(delta);
        this.draw();
    }

    private void update(float delta) {
        this.gameContent.update(delta, camera);
        this.updateCamera();
    }

    private void draw() {
        this.game.getBatch().begin();
        this.gameContent.render(this.game.getBatch(), this.game.getShapeRenderer());
        this.game.getBatch().end();

        this.game.getHudBatch().begin();
        this.gameHud.render(this.game.getHudBatch());
        this.game.getHudBatch().end();
    }

    private void updateCamera() {
        Vector2 vector2 = this.gameContent.getMainPlayer().getPosition();
        float x = Math.min(Math.max(vector2.x, 960), 2240);
        float y = Math.min(Math.max(vector2.y, 550), 2670);
        camera.position.set(x, y, 0);
        camera.update();
    }

}
