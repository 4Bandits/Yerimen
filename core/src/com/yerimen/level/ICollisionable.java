package com.yerimen.level;

import com.badlogic.gdx.math.Rectangle;
import com.yerimen.players.Character;
import com.yerimen.players.Player;

public interface ICollisionable {
    boolean isInCollision(Character character);

    boolean isInCollision(Rectangle bounds);

    void crashed(Player mainPlayer);
}
