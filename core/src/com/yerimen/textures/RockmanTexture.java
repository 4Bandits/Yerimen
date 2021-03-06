package com.yerimen.textures;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class RockmanTexture extends PlayerTexture {

    public RockmanTexture(Texture texture, Integer size) {
        this.texture = texture;
        standFrontAnimation = new Animation(0.3f, getFrames(texture, 0, 1, size));
        standBackAnimation = new Animation(0.3f, getFrames(texture, 3, 1, size));
        standLeftAnimation = new Animation(0.3f, getFrames(texture, 2, 1, size));
        standRightAnimation = new Animation(0.3f, getFrames(texture, 2, 1, size));
        walkFrontAnimation = new Animation(0.3f, getFrames(texture, 0, 4, size));
        walkBackAnimation = new Animation(0.3f, getFrames(texture, 3, 4, size));
        walkLeftAnimation = new Animation(0.3f, getFrames(texture, 1, 4, size));
        walkRightAnimation = new Animation(0.3f, getFrames(texture, 2, 4, size));
        /*attackFrontAnimation = new Animation(0.3f, getFrames(texture, 3, 4, size));
        attackBackAnimation = new Animation(0.3f, getFrames(texture, 2, 4, size));
        attackLeftAnimation = new Animation(0.3f, getFrames(texture, 1, 4, size));
        attackRightAnimation = new Animation(0.3f, getFrames(texture, 1, 4, size));
        deathAnimation = new Animation(0.3f, getFrames(texture, 0, 4, size));*/
    }
}
