package com.yerimen.players;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.textures.TextureManager;

public class NullPlayer extends MainPlayer {

    public NullPlayer(){
        super(TextureManager.getInstance().getWerewolfTexture(), new PlayerStatus(), new Vector2(0,0));
    }

    public void update(float deltaTime, OrthographicCamera camera){}
    public void render(SpriteBatch spriteBatch) {}

    public Vector2 getPosition(){
        return new Vector2(0,0);
    }

}
