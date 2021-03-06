package com.yerimen.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.yerimen.powers.Power;
import com.yerimen.powers.PowerFactory;
import com.yerimen.powers.PowerType;
import com.yerimen.server.Observable;
import com.yerimen.textures.PlayerTexture;

public class Player extends Character implements Observable {

    private String name;
    private Integer nextInt;
    private float cooldown;
    private double timer;
    private int currentSpeed;
    private Power power;
    public Vector2 previousPost;

    public Player(String characterID, String name, PlayerTexture playerTexture, CharacterStatus playerStatus, Vector2 position, Power power, String userName) {
        super(characterID, playerTexture, playerStatus, position,userName);
        this.name = name;
        this.nextInt = 0;
        this.currentSpeed = 1;
        this.power = power;
        this.cooldown = power.getCooldown();
        this.previousPost = new Vector2(position.x, position.y);
    }

    @Override
    public void update(float delta, OrthographicCamera camera) {
        stateTime += delta;
        processMove();
        processAttack(delta, camera);
        processSelectPower();

        if (this.isTakenDamage()) {

        }

        healthBar.update(getStatus().getHp(), getXPosition(), getYPosition() + 70);
    }

    private void processSelectPower() {
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1) || Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            this.setPower(PowerFactory.getPower(PowerType.Fireball));
            this.notifySkillChanged("Fire");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2) || Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            this.setPower(PowerFactory.getPower(PowerType.Iceball));
            this.notifySkillChanged("Ice");
        }
    }

    private void processAttack(float delta, OrthographicCamera camera) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && timer >= cooldown) {
            Vector3 mousePosition = this.getMousePosition(camera, Gdx.input.getX(), Gdx.input.getY());
            this.attack(mousePosition);
            timer = 0;
            cooldown = power.getCooldown();
        } else {
            timer += delta;
        }
    }

    private void processMove() {
        this.currentSpeed = 1;
        this.previousPost.set(getXPosition(), getYPosition());
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
        Power power = PowerFactory.getPower(this.getId(), this.getAttackID(), distance, vector2, this.getPosition(), this.power.getType());
        power.reproduceSound();
        this.notify(power);
    }

    private String getAttackID() {
        return this.getId() + this.getNextInt();
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
        this.respawndPoint.set(x, y);
    }

    private void translate(int x, int y, String direction, Animation animation) {
        this.translate(x, y).setDirection(direction);
        this.notify(this.toJson());
        setCurrentFrame(animation.getKeyFrame(stateTime, true));
    }

    private Player setPower(Power power) {
        this.power = power;
        return this;
    }

    public void onDead() {
        this.notifyKill();
    }

    public void increaseLife(int value) {
        if (getStatus().getHp() < 100){
            getStatus().increaseHp(value);
            this.notify(this.toJson());
        }

    }

    @Override
    public String getName() {
        return name;
    }
}
