package com.yerimen.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.json.PowerJsonBuilder;
import com.yerimen.players.Character;
import com.yerimen.screens.game.GameContent;
import org.json.JSONObject;

import java.util.List;

public class Power extends Sprite{

    private Float velocity;
    private Float distance;
    private Vector2 acceleration;
    private Vector2 destination;
    private Float time;
    private String characterID;
    private String attackID;

    public Power(String characterID, String attackID, Texture texture, Float velocity, Float distance, Vector2 destination, Vector2 startPosition){
        super(texture);
        this.characterID = characterID;
        this.attackID = attackID;
        this.setPosition(startPosition.x, startPosition.y);
        this.velocity = velocity;
        this.distance = distance;
        this.destination = destination;
        this.time = this.getTime();
        this.acceleration = getAcceleration();
    }

    private Vector2 getAcceleration() {
        return new Vector2((this.destination.x - this.getX()) / this.time, (this.destination.y - this.getY() )/ this.time);
    }

    private Float getTime(){
        return this.distance / this.velocity;
    }

    public void update(float deltaTime, List<Character> players, GameContent gameContent){
        this.translate(this.acceleration.x, this.acceleration.y);
        this.detectCollision(players, gameContent);
    }

    public void render(SpriteBatch spriteBatch){
        float angle = this.acceleration.angle();
        spriteBatch.draw(new TextureRegion(this.getTexture()), this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(),this.getScaleX(), this.getScaleY(), angle);
    }

    public JSONObject toJson(){
        return new PowerJsonBuilder(this).build();
    }

    public Float getVelocity() {
        return velocity;
    }

    public Float getDistance() {
        return distance;
    }

    public Vector2 getDestination() {
        return destination;
    }

    public String getCharacterID() {
        return characterID;
    }

    public String getAttackID() {
        return attackID;
    }

    private void detectCollision(List<Character> players, GameContent gameContent){
        for(Character player : players){
            if(this.isInCollisionWith(player)){
                this.notifyCollision(gameContent);
                player.isAttacked();
            }
        }
    }

    private Boolean isInCollisionWith(Character player){
        return !player.getCharacterID().contentEquals(this.getCharacterID()) && this.getBoundingRectangle().overlaps(player.getBounds());
    }

    private void notifyCollision(GameContent gameContent){
        gameContent.removePower(this);
    }



}
