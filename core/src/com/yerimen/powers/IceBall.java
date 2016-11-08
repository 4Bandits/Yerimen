package com.yerimen.powers;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.sounds.SoundManager;
import com.yerimen.textures.TextureManager;

public class IceBall extends Power {

    public IceBall() {
        super(8, 0.9f);
    }

    public IceBall(String characterID, String attackID, Float distance, Vector2 destination, Vector2 startPosition) {
        super(characterID, attackID, TextureManager.getInstance().getIceBall(), 10f, distance, destination, startPosition, 8, 0.9f);
    }

    @Override
    public PowerType getType() {
        return PowerType.Iceball;
    }

    @Override
    public void reproduceSound() {
        Sound sound = SoundManager.getInstance().getFireball();
        sound.play(0.8f);
    }
}
