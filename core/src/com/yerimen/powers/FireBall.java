package com.yerimen.powers;

import com.badlogic.gdx.math.Vector2;
import com.yerimen.textures.TextureManager;

public class FireBall extends Power {

    public FireBall(Float distance, Vector2 destination, Vector2 startPosition) {
        super(TextureManager.getInstance().getFireBall(), 10f, distance, destination, startPosition);
    }
}
