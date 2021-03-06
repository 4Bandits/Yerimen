package com.yerimen.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class VampireTexture extends PlayerTexture {

    public VampireTexture(Texture texture, Integer size) {
        this.texture = texture;
        standFrontAnimation = new Animation(0.3f, getFrames(texture, 0, 1, size));
        standBackAnimation = new Animation(0.3f, getFrames(texture, 3, 1, size));
        standLeftAnimation = new Animation(0.3f, getFrames(texture, 2, 1, size));
        standRightAnimation = new Animation(0.3f, getFrames(texture, 2, 1, size));
        walkFrontAnimation = new Animation(0.3f, getFrames(texture, 0, 4, size));
        walkBackAnimation = new Animation(0.3f, getFrames(texture, 3, 4, size));
        walkLeftAnimation = new Animation(0.3f, getFrames(texture, 1, 4, size));
        walkRightAnimation = new Animation(0.3f, getFrames(texture, 2, 4, size));
        this.name = "Vampire";
    }
}
