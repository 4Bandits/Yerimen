package com.yerimen.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.server.Observable;
import com.yerimen.textures.PlayerTexture;

public class Player extends Character implements Observable {
    public Player(PlayerTexture playerTexture, CharacterStatus playerStatus, Vector2 position) {
        super(playerTexture, playerStatus, position);
    }

    @Override
    public void update(float delta, OrthographicCamera camera) {
        stateTime += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.translate(0, -1).setDirection("up");
            this.notify(this.toJson());
            setCurrentFrame(playerTexture.getWalkBackAnimation().getKeyFrame(stateTime, true));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.translate(-1, 0).setDirection("right");
            this.notify(this.toJson());
            setCurrentFrame(playerTexture.getWalkRightAnimation().getKeyFrame(stateTime, true));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.translate(0, 1).setDirection("down");
            this.notify(this.toJson());
            setCurrentFrame(playerTexture.getWalkFrontAnimation().getKeyFrame(stateTime, true));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.translate(1, 0).setDirection("left");
            this.notify(this.toJson());
            setCurrentFrame(playerTexture.getWalkLeftAnimation().getKeyFrame(stateTime, true));
        }
    }
}
