package com.yerimen.screens.mainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.yerimen.YerimenGame;
import com.yerimen.screens.GameStateManager;
import com.yerimen.screens.State;

public class MainMenuScreen extends State {

    private MainMenuContent content;
    private OrthographicCamera camera;
    private Stage stage;

    public MainMenuScreen(GameStateManager gsm) {
        super(gsm);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        this.content = new MainMenuContent(gsm);
        this.initializeCamera();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        this.stage.addActor(new Background());
        this.stage.addActor(this.content.getContent());
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act();
        this.stage.draw();
    }
/*
    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, false);
    }
*/
    private void initializeCamera(){
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.update();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float delta) {

    }


    @Override
    public void dispose() {

    }
}
