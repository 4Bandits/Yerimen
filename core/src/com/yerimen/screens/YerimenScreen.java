package com.yerimen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public abstract class YerimenScreen {

    protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected ScreenManager gameScreenManager;

    protected YerimenScreen(ScreenManager gameScreenManager){
        this.gameScreenManager = gameScreenManager;
        this.camera = new OrthographicCamera();
        this.mouse = new Vector3();

        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    protected abstract void handleInput();

    public abstract void update(float delta);

    public abstract void render(SpriteBatch spriteBatch, SpriteBatch hudBatch, ShapeRenderer shapeRenderer);

    public abstract void dispose();

}
