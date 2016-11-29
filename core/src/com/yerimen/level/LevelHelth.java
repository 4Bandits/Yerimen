package com.yerimen.level;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.yerimen.players.Character;
import com.yerimen.players.Player;

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
        return this.sprite.getBoundingRectangle().overlaps(bounds);
    }

    @Override
    public void crashed(Player mainPlayer) {
        mainPlayer.increaseLife(1);
    }
}
