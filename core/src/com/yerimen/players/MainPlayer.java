package com.yerimen.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.server.Observable;
import com.yerimen.textures.PlayerTexture;

public class MainPlayer extends Player implements Observable {

    public MainPlayer(PlayerTexture playerTexture, PlayerStatus playerStatus, Vector2 position){
        super(playerTexture, playerStatus, position);
    }

    public void update(float spriteBatch, OrthographicCamera camera){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            this.translate(10,0);
            this.notify(this.toJson());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            this.translate(-10,0);
            this.notify(this.toJson());
        }
    }

    public void render(SpriteBatch spriteBatch) {
        this.draw(spriteBatch);
    }


}
