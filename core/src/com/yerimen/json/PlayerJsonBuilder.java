package com.yerimen.json;

import com.yerimen.players.Character;
import org.json.JSONObject;

public class PlayerJsonBuilder {

    private float x;
    private float y;
    private Double health;
    private String direction;
    private String name;

    public PlayerJsonBuilder(Character player){
        x = player.getXPosition();
        y = player.getYPosition();
        health = player.getStatus().getHp();
        direction = player.getDirection();
        name = player.getName();
    }

    public JSONObject build(){
        JSONObject data = new JSONObject();
        try {
            data.put("x", x);
            data.put("y", y);
            data.put("health", health);
            data.put("direction", direction);
            data.put("name", name);
            return data;
        }catch (Exception e){
            throw new RuntimeException("Error - Json");
        }
    }

}