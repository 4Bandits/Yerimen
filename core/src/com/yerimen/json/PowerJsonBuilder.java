package com.yerimen.json;

import com.badlogic.gdx.math.Vector2;
import com.yerimen.powers.FireBall;
import com.yerimen.powers.Power;
import org.json.JSONException;
import org.json.JSONObject;

public class PowerJsonBuilder {

    private String id;
    private Float x;
    private Float y;
    private String name;
    private Float velocity;
    private Float distance;
    private Float destinationX;
    private Float destinationY;

    public PowerJsonBuilder(Power power){
        this.id = "";//power.getId();
        this.x = power.getX();
        this.y = power.getY();
        this.name = "images/fireBall.png";
        this.velocity = power.getVelocity();
        this.distance = power.getDistance();
        this.destinationX = power.getDestination().x;
        this.destinationY = power.getDestination().y;
    }

    public JSONObject build(){
        JSONObject data = new JSONObject();
        try {
            data.put("id", id);
            data.put("x", x);
            data.put("y", y);
            data.put("name", name);
            data.put("velocity", velocity);
            data.put("distance", distance);
            data.put("destinationX", destinationX);
            data.put("destinationY", destinationY);
            return data;
        }catch (Exception e){
            throw new RuntimeException("Error - Json");
        }
    }

    public PowerJsonBuilder(JSONObject jsonObject){
        try {
            id = jsonObject.getString("id");
            x = new Float(jsonObject.getDouble("x"));
            y = new Float(jsonObject.getDouble("y"));
            name = jsonObject.getString("name");
            velocity = new Float(jsonObject.getDouble("velocity"));
            distance = new Float(jsonObject.getDouble("distance"));
            destinationX = new Float(jsonObject.getDouble("destinationX"));
            destinationY = new Float(jsonObject.getDouble("destinationY"));
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Move Player Error");
        }
    }

    public Power buildObject(){
        return new FireBall(this.distance, new Vector2(destinationX, destinationY), new Vector2(x,y));
    }
}
