package com.yerimen.players;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.json.PlayerJsonBuilder;
import com.yerimen.textures.PlayerTexture;
import org.json.JSONObject;

public class Player {

    private PlayerStatus status;
    private PlayerTexture playerTexture;
    private String direction;
    private Sprite sprite;

    public Player(PlayerTexture playerTexture, PlayerStatus playerStatus, Vector2 position){
        sprite = new Sprite(playerTexture.getTexture());
        this.playerTexture = playerTexture;
        this.sprite.setPosition(position.x, position.y);
        this.status = playerStatus;
        this.direction = "left";
    }

    public void render(SpriteBatch spriteBatch) {
        this.sprite.draw(spriteBatch);
    }

    public Vector2 getPosition(){
        return new Vector2(this.sprite.getX(), this.sprite.getY());
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public String getName(){
        return this.playerTexture.getName();
    }

    public String getDirection(){
        return this.direction;
    }

    public void setDirection(String direction) {this.direction = direction; }

    public void move(float x, float y){
        sprite.setPosition(x,y);
    }
    public float getXPosition(){
        return sprite.getY();
    }

    public float getYPosition(){
        return sprite.getY();
    }
    JSONObject toJson(){
        return new PlayerJsonBuilder(this).build();
    }

    public void translate(float x, float y) {
        sprite.translate(x,y);
    }
}
