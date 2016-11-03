package com.yerimen.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.yerimen.powers.FireBall;
import com.yerimen.powers.Power;
import com.yerimen.server.Observable;
import com.yerimen.textures.PlayerTexture;

public class Player extends Character implements Observable {

    private Integer nextInt;
    private int currentSpeed;

    public Player(String characterID, PlayerTexture playerTexture, CharacterStatus playerStatus, Vector2 position) {
        super(characterID, playerTexture, playerStatus, position);
        this.nextInt = 0;
        this.currentSpeed = 1;
    }

    @Override
    public void update(float delta, OrthographicCamera camera) {
        stateTime += delta;
        processMove();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 mousePosition = this.getMousePosition(camera, Gdx.input.getX(), Gdx.input.getY());
            this.attack(mousePosition);
        }
        if (this.isTakenDamage()) {

        }
    }

    private void processMove() {
        this.currentSpeed = 1;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.currentSpeed = 4;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.translate(0, currentSpeed, "up", playerTexture.getWalkBackAnimation());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.translate(currentSpeed, 0, "right", playerTexture.getWalkRightAnimation());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.translate(0, -currentSpeed, "down", playerTexture.getWalkFrontAnimation());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.translate(-currentSpeed, 0, "left", playerTexture.getWalkLeftAnimation());
        }
    }

    private void attack(Vector3 vector3) {
        Vector2 vector2 = new Vector2(vector3.x, vector3.y);
        Float distance = this.getPosition().dst(vector2);
        Power power = new FireBall(this.getCharacterID(), this.getAttackID(), distance, vector2, this.getPosition());
        this.notify(power);
    }

    private String getAttackID() {
        return this.getCharacterID() + this.getNextInt();
    }

    private String getNextInt() {
        String ret = this.nextInt.toString();
        this.nextInt++;
        return ret;
    }

    private boolean isTakenDamage() {
        return false;
    }

    private Vector3 getMousePosition(OrthographicCamera camera, float x, float y) {
        Vector3 vector3 = new Vector3();
        vector3.set(x, y, 0);
        return camera.unproject(vector3);
    }

    public void setPosition(float x, float y) {
        this.sprite.setPosition(x, y);
    }

    private void translate(int x, int y, String direction, Animation animation) {
        this.translate(x, y).setDirection(direction);
        this.notify(this.toJson());
        setCurrentFrame(animation.getKeyFrame(stateTime, true));
    }
}
