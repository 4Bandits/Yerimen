package com.yerimen.screens.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Skill {

    private BitmapFont bitmap;
    private Vector2 position;
    private String content;
    private Texture texture;
    private float alpha;

    public Skill(float x, float y, String content, Texture texture) {
        this.bitmap = new BitmapFont();
        this.bitmap.getData().setScale(1.2f);
        this.bitmap.setColor(1, 1, 1, 1);
        this.position = new Vector2(x, y);
        this.texture = texture;
        this.content = content;
        this.alpha = 1;
    }

    public void render(SpriteBatch spriteBatch) {
        this.drawTextureWithAlpha(spriteBatch);
        this.bitmap.draw(spriteBatch, this.content, this.position.x + 20, this.position.y);
    }

    public void show() {
        this.alpha = 1;
    }

    public void hide() {
        this.alpha = 0.4f;
    }

    private void drawTextureWithAlpha(SpriteBatch spriteBatch) {
        Color currentColor = spriteBatch.getColor();
        float oldAlpha = currentColor.a;
        currentColor.a = this.alpha;
        spriteBatch.setColor(currentColor);
        spriteBatch.draw(texture, position.x, position.y, 50, 50);
        currentColor.a = oldAlpha;
        spriteBatch.setColor(currentColor);
    }

}
