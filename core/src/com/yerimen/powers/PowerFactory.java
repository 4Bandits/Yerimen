package com.yerimen.powers;


import com.badlogic.gdx.math.Vector2;

public class PowerFactory {
    public static Power getPower(PowerType type) {
        Power power;//todo refactor
        if (type == PowerType.Fireball) {
            power = new FireBall();
        } else {
            power = new IceBall();
        }
        return power;
    }

    public static Power getPower(String characterId, String attackId, Float distance, Vector2 destination, Vector2 startPosition, PowerType type) {
        Power power;//todo refactor
        if (type == PowerType.Fireball) {
            power = new FireBall(characterId, attackId, distance, destination, startPosition);
        } else {
            power = new IceBall(characterId, attackId, distance, destination, startPosition);
        }
        return power;
    }
}
