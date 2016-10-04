package com.yerimen.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class WizardTexture extends PlayerTexture{

    public WizardTexture(Texture texture, Integer size){
        this.texture = texture;
        standFrontAnimation = new Animation(0.3f, getFrames(texture, 9, 6, size));
        standBackAnimation = new Animation(0.3f, getFrames(texture, 8, 6, size));
        standLeftAnimation = new Animation(0.3f, getFrames(texture, 7, 6, size));
        standRightAnimation = new Animation(0.3f, getFrames(texture, 7, 6, size));
        walkFrontAnimation = new Animation(0.3f, getFrames(texture, 6, 4, size));
        walkBackAnimation = new Animation(0.3f, getFrames(texture, 5, 4, size));
        walkLeftAnimation = new Animation(0.3f, getFrames(texture, 4, 4, size));
        walkRightAnimation = new Animation(0.3f, getFrames(texture, 4, 4, size));
        attackFrontAnimation = new Animation(0.3f, getFrames(texture, 3, 4, size));
        attackBackAnimation = new Animation(0.3f, getFrames(texture, 2, 4, size));
        attackLeftAnimation = new Animation(0.3f, getFrames(texture, 1, 4, size));
        attackRightAnimation = new Animation(0.3f, getFrames(texture, 1, 4, size));
        deathAnimation = new Animation(0.3f, getFrames(texture, 0, 4, size));
    }
}
