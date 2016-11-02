package com.yerimen.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by nacho on 11/1/2016.
 */
public class MiniMap {

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.CHARTREUSE);
        shapeRenderer.rect(10, 10,130, 130);
        shapeRenderer.end();
        batch.begin();
    }
}
