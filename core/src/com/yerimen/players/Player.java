package com.yerimen.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.yerimen.powers.FireBall;
import com.yerimen.powers.Power;
import com.yerimen.server.Observable;
import com.yerimen.textures.PlayerTexture;

public class Player extends Character implements Observable {
    public Player(PlayerTexture playerTexture, CharacterStatus playerStatus, Vector2 position) {
        super(playerTexture, playerStatus, position);
    }

    @Override
    public void update(float delta, OrthographicCamera camera) {
        stateTime += delta;
        processMove();
        processAttack(camera);
        processEnemyAttacks();
    }

    private void processMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.translate(0, -1).setDirection("up");
            setCurrentFrame(playerTexture.getWalkBackAnimation().getKeyFrame(stateTime, true));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.translate(-1, 0).setDirection("right");
            setCurrentFrame(playerTexture.getWalkRightAnimation().getKeyFrame(stateTime, true));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.translate(0, 1).setDirection("down");
            setCurrentFrame(playerTexture.getWalkFrontAnimation().getKeyFrame(stateTime, true));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.translate(1, 0).setDirection("left");
            setCurrentFrame(playerTexture.getWalkLeftAnimation().getKeyFrame(stateTime, true));
        }
        this.notify(this.toJson());
    }

    private void processEnemyAttacks() {
        if (this.hasTakenDamage()) {

        }
    }

    private void processAttack(OrthographicCamera camera) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 mousePosition = this.getMousePosition(camera, Gdx.input.getX(), Gdx.input.getY());
            this.attack(mousePosition);
        }
    }

    private void attack(Vector3 vector3) {
        Vector2 vector2 = new Vector2(vector3.x, vector3.y);
        Float distance = this.getPosition().dst(vector2);
        Power power = new FireBall(distance, vector2, this.getPosition());
        this.notify(power);
    }

    private Vector3 getMousePosition(OrthographicCamera camera, float x, float y) {
        Vector3 vector3 = new Vector3();
        vector3.set(x, y, 0);
        return camera.unproject(vector3);
    }

    private boolean hasTakenDamage() {
        return false;
    }
}
