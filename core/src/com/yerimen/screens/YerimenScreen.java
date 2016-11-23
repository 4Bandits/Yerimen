package com.yerimen.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public abstract class YerimenScreen {
    protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected ScreenManager gsm;

    protected YerimenScreen(ScreenManager gsm){
        this.gsm = gsm;
        this.camera = new OrthographicCamera();
        this.mouse = new Vector3();
    }

    protected abstract void handleInput();

    public abstract void update(float delta);

    public abstract void render(SpriteBatch sb, ShapeRenderer sr);
    public abstract void dispose();
}
