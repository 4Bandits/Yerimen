package com.yerimen.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.server.Observable;
import com.yerimen.textures.PlayerTexture;

public class Player extends Character implements Observable {


    float stateTime;
    public Player(PlayerTexture playerTexture, CharacterStatus playerStatus, Vector2 position){
        super(playerTexture, playerStatus, position);
    }

    public void update(float delta, OrthographicCamera camera){
        isMoving = false;
        PlayerTexture texture = getPlayerTexture();
        stateTime+=delta;
      //  setCurrentFrame(texture.getStandBackAnimation().getKeyFrame(delta,true));
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            this.translate(0,-1);
            this.notify(this.toJson());
            setCurrentFrame(texture.getWalkBackAnimation().getKeyFrame(stateTime,true));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            this.translate(-1,0);
            this.notify(this.toJson());
            setCurrentFrame(texture.getWalkRightAnimation().getKeyFrame(stateTime,true));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            this.translate(0,1);
            this.notify(this.toJson());
            setCurrentFrame(texture.getWalkFrontAnimation().getKeyFrame(stateTime,true));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            this.translate(1,0);
            this.notify(this.toJson());
            setCurrentFrame(texture.getWalkLeftAnimation().getKeyFrame(stateTime,true));
        }
        if(isMoving){

        }else{

        }
    }
}
