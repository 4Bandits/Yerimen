package com.yerimen.powers;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.sounds.SoundManager;
import com.yerimen.textures.TextureManager;

public class FireBall extends Power {

    public FireBall() {
        super(5, 0.5f);
    }

    public FireBall(String characterID, String attackID, Float distance, Vector2 destination, Vector2 startPosition) {
        super(characterID, attackID, TextureManager.getInstance().getFireBall(), 10f, distance, destination, startPosition, 5, 0.5f);
    }

    @Override
    public PowerType getType() {
        return PowerType.Fireball;
    }

    @Override
    public void reproduceSound() {
        Sound sound = SoundManager.getInstance().getFireball();
        sound.play(0.8f);
    }
}
