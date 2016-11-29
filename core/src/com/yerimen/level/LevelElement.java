package com.yerimen.level;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class LevelElement {
    protected Sprite sprite;

    public LevelElement(Texture texture, float x, float y){
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
    }

    public abstract void render(SpriteBatch batch);
}
