package com.yerimen.screens.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Notification {

    private BitmapFont bitmap;
    private Vector2 position;
    private GlyphLayout layout;
    private float alpha;
    private float textWidth;
    private String content;

    public Notification(String content) {
        this.bitmap = new BitmapFont();
        this.bitmap.getData().setScale(2);
        this.alpha = 1;

        this.layout = new GlyphLayout();
        this.content = content;
        this.layout.setText(this.bitmap, this.content);

        this.textWidth = this.layout.width;
        this.position = new Vector2(middleScreenWidth(), middleScreenHeight());
    }

    public void shutdown() {
        this.alpha = 0;
    }

    public boolean isOnScreen() {
        return this.alpha > 0;
    }

    public void render(SpriteBatch spriteBatch) {
        if(this.alpha > 0) {
            this.bitmap.setColor(1, 1, 1, alpha);
            this.bitmap.draw(spriteBatch, this.content, this.middleScreenWidth(), this.middleScreenHeight());
            this.alpha -= 0.005;
        }
    }

    private float middleScreenWidth() {
        return Gdx.graphics.getWidth() / 2 - (this.textWidth / 2);
    }

    private float middleScreenHeight() {
        float offset = Gdx.graphics.getHeight() / 6;
        return Gdx.graphics.getHeight() - offset;
    }
}

