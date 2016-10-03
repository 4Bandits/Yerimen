package com.yerimen.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerTexture {

    protected Texture texture;
    protected Animation standFrontAnimation;
    protected Animation standBackAnimation;
    protected Animation standLeftAnimation;
    protected Animation standRightAnimation;
    protected Animation walkFrontAnimation;
    protected Animation walkBackAnimation;
    protected Animation walkLeftAnimation;
    protected Animation walkRightAnimation;
    protected Animation attackFrontAnimation;
    protected Animation attackBackAnimation;
    protected Animation attackLeftAnimation;
    protected Animation attackRightAnimation;
    protected Animation deathAnimation;
    protected String name;

    public Texture getTexture() {
        return texture;
    }

    public Animation getStandFrontAnimation() {
        return standFrontAnimation;
    }

    public Animation getStandBackAnimation() {
        return standBackAnimation;
    }

    public Animation getStandLeftAnimation() {
        return standLeftAnimation;
    }

    public Animation getStandRightAnimation() {
        return standRightAnimation;
    }

    public Animation getWalkFrontAnimation() {
        return walkFrontAnimation;
    }

    public Animation getWalkBackAnimation() {
        return walkBackAnimation;
    }

    public Animation getWalkLeftAnimation() {
        return walkLeftAnimation;
    }

    public Animation getWalkRightAnimation() {
        return walkRightAnimation;
    }

    public Animation getAttackFrontAnimation() {
        return attackFrontAnimation;
    }

    public Animation getAttackBackAnimation() {
        return attackBackAnimation;
    }

    public Animation getAttackLeftAnimation() {
        return attackLeftAnimation;
    }

    public Animation getAttackRightAnimation() {
        return attackRightAnimation;
    }

    public Animation getDeathAnimation() {
        return deathAnimation;
    }

    public String getName() {
        return name;
    }

    protected TextureRegion[] getFrames(Texture texture, int row, int columns, int size) {
        TextureRegion[][] tmp = TextureRegion.split(texture, size, size);
        TextureRegion[] frames = new TextureRegion[columns];
        int index = 0;
        for (int i = 0; i < columns; i++) {
            frames[index++] = tmp[row][i];
        }
        return frames;
    }
}
