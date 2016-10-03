package com.yerimen.players;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.json.PlayerJsonBuilder;
import com.yerimen.textures.PlayerTexture;
import org.json.JSONObject;

public class Player extends Sprite {

    private PlayerStatus status;
    private PlayerTexture playerTexture;
    private String direction;

    public Player(PlayerTexture playerTexture, PlayerStatus playerStatus, Vector2 position){
        super(playerTexture.getTexture());
        this.playerTexture = playerTexture;
        this.setPosition(position.x, position.y);
        this.status = playerStatus;
        this.direction = "left";
    }

    public void render(SpriteBatch spriteBatch) {
        this.draw(spriteBatch);
    }

    public Vector2 getPosition(){
        return new Vector2(this.getX(), this.getY());
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

    JSONObject toJson(){
        return new PlayerJsonBuilder(this).build();
    }
}
