package com.yerimen.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class SoundManager {

    private static SoundManager instance = null;
    private Sound fireball;
    private Sound iceball;
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private SoundManager(){
        initializePowers();
    }

    private void initializePowers() {
        fireball = new Gdx().audio.newSound(Gdx.files.internal("sounds/fireball.mp3"));
        iceball = new Gdx().audio.newSound(Gdx.files.internal("sounds/fireball.mp3"));
    }

    public Sound getFireball() {
        return fireball;
    }

}
