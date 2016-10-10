package com.yerimen.screens.mainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.yerimen.YerimenGame;
import com.yerimen.screens.game.GameContent;
import com.yerimen.screens.game.YerimenScreen;
import sun.font.GraphicComponent;

public class MainMenuScreen extends ScreenAdapter {

    private YerimenGame game;
    private MainMenuContent content;
    private OrthographicCamera camera;
    private Stage stage;

    public MainMenuScreen(YerimenGame game) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        this.game = game;
        this.content = new MainMenuContent(game);
        this.initializeCamera();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        this.stage.addActor(new Background());
        this.stage.addActor(this.content.getContent());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act();
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, false);
    }

    private void initializeCamera(){
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.update();
    }

}
