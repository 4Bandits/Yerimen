package com.yerimen.json;

import com.badlogic.gdx.math.Vector2;
import com.yerimen.powers.Power;
import com.yerimen.powers.PowerFactory;
import org.json.JSONException;
import org.json.JSONObject;

public class PowerJsonBuilder {

    private String characterID;
    private String attackID;
    private Float x;
    private Float y;
    private String type;
    private Float velocity;
    private Float distance;
    private Float destinationX;
    private Float destinationY;

    public PowerJsonBuilder(Power power) {
        this.characterID = power.getCharacterID();
        this.attackID = power.getAttackID();
        this.x = power.getX();
        this.y = power.getY();
        this.type = power.getType();
        this.velocity = power.getVelocity();
        this.distance = power.getDistance();
        this.destinationX = power.getDestination().x;
        this.destinationY = power.getDestination().y;
    }

    public JSONObject build() {
        JSONObject data = new JSONObject();
        try {
            data.put("characterID", characterID);
            data.put("attackID", attackID);
            data.put("x", x);
            data.put("y", y);
            data.put("type", type);
            data.put("velocity", velocity);
            data.put("distance", distance);
            data.put("destinationX", destinationX);
            data.put("destinationY", destinationY);
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Error - Json");
        }
    }

    public PowerJsonBuilder(JSONObject jsonObject) {
        try {
            characterID = jsonObject.getString("characterID");
            attackID = jsonObject.getString("attackID");
            x = new Float(jsonObject.getDouble("x"));
            y = new Float(jsonObject.getDouble("y"));
            type = jsonObject.getString("type");
            velocity = new Float(jsonObject.getDouble("velocity"));
            distance = new Float(jsonObject.getDouble("distance"));
            destinationX = new Float(jsonObject.getDouble("destinationX"));
            destinationY = new Float(jsonObject.getDouble("destinationY"));
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Move Player Error");
        }
    }

    public Power buildObject() {
        return PowerFactory.getPower(this.characterID, this.attackID, this.distance, new Vector2(destinationX, destinationY), new Vector2(x, y), type);
    }
}
