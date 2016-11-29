package com.yerimen.level;

import com.badlogic.gdx.math.Rectangle;
import com.yerimen.players.Character;

/**
 * Created by nacho on 11/29/2016.
 */
public interface ICollisionable {
    boolean isInCollision(Character character);
    boolean isInCollision(Rectangle bounds);
}
