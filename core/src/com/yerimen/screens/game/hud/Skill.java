package com.yerimen.screens.game.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Skill {

    private BitmapFont bitmap;
    private Vector2 position;
    private String content;
    private Texture texture;

    public Skill(float x, float y, String content, Texture texture) {
        this.bitmap = new BitmapFont();
        this.bitmap.getData().setScale(2);
        this.bitmap.setColor(1, 1, 1, 1);
        this.position = new Vector2(x, y);
        this.texture = texture;
        this.content = content;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, position.x, position.y);
        this.bitmap.draw(spriteBatch, this.content, this.position.x, this.position.y);
    }

    public void show() {
        this.bitmap.setColor(1, 1, 1, 1);
    }

    public void hide() {
        this.bitmap.setColor(1, 1, 1, 0.4f);
    }

}
