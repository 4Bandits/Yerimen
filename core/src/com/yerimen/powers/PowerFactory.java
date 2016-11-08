package com.yerimen.powers;


import com.badlogic.gdx.math.Vector2;

public class PowerFactory {
    public static Power getPower(String type) {
        Power power;
        if (type.equals("fireball")  ) {
            power = new FireBall();
        } else {
            power = new IceBall();
        }
        return power;
    }

    public static Power getPower(String characterId, String attackId, Float distance, Vector2 destination, Vector2 startPosition, String type) {
        Power power;
        if (type.equals("fireball")  ) {
            power = new FireBall(characterId, attackId, distance, destination, startPosition);
        } else {
            power = new IceBall(characterId, attackId, distance, destination, startPosition);
        }
        return power;
    }
}
