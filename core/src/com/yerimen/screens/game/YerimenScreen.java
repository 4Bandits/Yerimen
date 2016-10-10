package com.yerimen.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.YerimenGame;
import com.yerimen.user.UserInformation;

public class YerimenScreen  extends ScreenAdapter {

    private YerimenGame game;
    private GameContent gameContent;
    private OrthographicCamera camera;

    public YerimenScreen(YerimenGame game, UserInformation userInformation) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        this.game = game;
        this.gameContent = new GameContent();
        this.initializeCamera();
    }

    private void initializeCamera(){
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
        this.game.batch.begin();
        this.gameContent.render(this.game.getBatch());
        this.game.batch.end();
    }

    private void updateCamera(){
        Vector2 vector2 = this.gameContent.getMainPlayer().getPosition();
        camera.position.set(vector2.x, vector2.y, 0);
        camera.update();
    }

}
