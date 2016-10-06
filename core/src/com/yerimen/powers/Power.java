package com.yerimen.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.json.PowerJsonBuilder;
import org.json.JSONObject;

public class Power extends Sprite {

    private Float velocity;
    private Float distance;
    private Vector2 acceleration;
    private Vector2 destination;
    private Float time;

    public Power(Texture texture, Float velocity, Float distance, Vector2 destination, Vector2 startPosition) {
        super(texture);
        this.setPosition(startPosition.x, startPosition.y);
        this.velocity = velocity;
        this.distance = distance;
        this.destination = destination;
        this.time = this.getTime();
        this.acceleration = getAcceleration();
    }

    private Vector2 getAcceleration() {
        return new Vector2((this.destination.x - this.getX()) / this.time, (this.destination.y - this.getY()) / this.time);
    }

    private Float getTime(){
        return this.distance / this.velocity;
    }

    public void update(float deltaTime){
        this.translate(this.acceleration.x, this.acceleration.y);
    }

    public void render(SpriteBatch spriteBatch){
        this.draw(spriteBatch);
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
}
