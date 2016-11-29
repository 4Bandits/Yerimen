package com.yerimen.level;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.players.Character;

public class LevelHelth extends LevelElement implements ICollisionable {

    public LevelHelth(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void render(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    @Override
    public boolean isInCollision(Character character) {
        return false;
    }

    @Override
    public boolean isInCollision(Rectangle bounds) {
        return false;
    }
}
