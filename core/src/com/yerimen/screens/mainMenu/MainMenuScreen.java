package com.yerimen.screens.mainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.yerimen.screens.ScreenManager;
import com.yerimen.screens.YerimenScreen;

public class MainMenuScreen extends YerimenScreen {

    private MainMenuContent content;
    private OrthographicCamera camera;
    private Stage stage;

    public MainMenuScreen(ScreenManager gameScreenManager) {
        super(gameScreenManager);
        this.content = new MainMenuContent(gameScreenManager);

        this.initializeCamera();
        this.initializeStage();
    }

    @Override
    public void render(SpriteBatch spriteBatch, SpriteBatch hudBatch, ShapeRenderer shapeRenderer) {
        this.stage.act();
        this.stage.draw();
    }

    /*
    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, false);
    }
    */

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }

    private void initializeCamera(){
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.update();
    }

    private void initializeStage() {
        this.stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        this.stage.addActor(new Background());
        this.stage.addActor(this.content.getContent());
    }

}
