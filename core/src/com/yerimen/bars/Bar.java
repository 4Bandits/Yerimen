package com.yerimen.bars;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bar {

    private NinePatch bar;
    private NinePatch container;
    private int totalWidth;
    private int totalHeight;
    private float percentage;
    private float positionX;
    private float positionY;

    public Bar(Texture content, Texture barContainer, float x, float y, int height, int width) {
        bar = new NinePatch(content, 0, 0, 0, 0);
        container = new NinePatch(barContainer, 0, 0, 0, 0);
        positionX = x;
        positionY = y;
        totalWidth = width;
        totalHeight = height;
        percentage = width / 100f;
    }

    public void update(int width, float x, float y) {
        percentage = width / 100f;
        positionX = x + 5;
        positionY = y + 5;
    }

    public void render(SpriteBatch batch) {
        container.draw(batch, positionX, positionY, totalWidth, totalHeight);
        bar.draw(batch, positionX, positionY, totalWidth * percentage, totalHeight);
    }
}
