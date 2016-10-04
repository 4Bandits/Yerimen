package com.yerimen.players;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yerimen.json.PlayerJsonBuilder;
import com.yerimen.textures.PlayerTexture;
import org.json.JSONObject;

public class Character {

    private CharacterStatus status;
    private PlayerTexture playerTexture;
    private String direction;
    private Sprite sprite;
    public boolean isMoving;
    private TextureRegion currentFrame;

    public Character(PlayerTexture playerTexture, CharacterStatus playerStatus, Vector2 position){
        sprite = new Sprite(playerTexture.getTexture());
        this.playerTexture = playerTexture;
        this.sprite.setPosition(position.x, position.y);
        this.status = playerStatus;
        this.direction = "left";
        isMoving = false;
        currentFrame =  playerTexture.getStandBackAnimation().getKeyFrame(0,true);

    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(currentFrame,80,80);
        //this.sprite.draw(spriteBatch);
    }

    public Vector2 getPosition(){
        return new Vector2(this.sprite.getX(), this.sprite.getY());
    }

    public CharacterStatus getStatus() {
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
        return sprite.getX();
    }

    public float getYPosition(){
        return sprite.getY();
    }

    JSONObject toJson(){
        return new PlayerJsonBuilder(this).build();
    }

    public void translate(float x, float y) {
        sprite.translate(x,y);
        isMoving = true;
    }

    public PlayerTexture getPlayerTexture(){
        return playerTexture;
    }

    public void setCurrentFrame(TextureRegion frame){
        currentFrame = frame;
    }
}
