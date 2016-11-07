package com.yerimen.powers;

import com.badlogic.gdx.math.Vector2;
import com.yerimen.textures.TextureManager;

public class FireBall extends Power {

    public FireBall(String characterID, String attackID, Float distance, Vector2 destination, Vector2 startPosition) {
        super(characterID, attackID, TextureManager.getInstance().getFireBall(), 10f, distance, destination, startPosition, 5);
    }
}
