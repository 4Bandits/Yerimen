package com.yerimen.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.yerimen.powers.FireBall;
import com.yerimen.powers.Power;
import com.yerimen.server.Observable;
import com.yerimen.textures.PlayerTexture;

public class MainPlayer extends Player implements Observable {

    public MainPlayer(PlayerTexture playerTexture, PlayerStatus playerStatus, Vector2 position){
        super(playerTexture, playerStatus, position);
    }

    public void update(float delta, OrthographicCamera camera){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.A) ){
            this.translate(10, 0);
            this.notify(this.toJson());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.D)){
            this.translate(-10, 0);
            this.notify(this.toJson());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
            this.translate(0, -10);
            this.notify(this.toJson());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
            this.translate(0, 10);
            this.notify(this.toJson());
        }
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            Vector3 mousePosition = this.getMousePosition(camera, Gdx.input.getX(),Gdx.input.getY());
            this.attack(mousePosition);
        }
        if(this.isTakenDamage()){

        }
    }

    public void render(SpriteBatch spriteBatch) {
        this.draw(spriteBatch);
    }

    private void attack(Vector3 vector3){
        Vector2 vector2 = new Vector2(vector3.x, vector3.y);
        Float distance = this.getPosition().dst(vector2);
        Power power = new FireBall(distance, vector2, this.getPosition());
        this.notify(power);
    }

    private boolean isTakenDamage(){
        return false;
    }

    private Vector3 getMousePosition(OrthographicCamera camera, float x, float y){
        Vector3 vector3 = new Vector3();
        vector3.set(x,y,0);
        return camera.unproject(vector3);
    }

}
