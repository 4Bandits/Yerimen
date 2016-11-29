package com.yerimen.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yerimen.textures.TextureManager;

import java.util.ArrayList;

public class Level {

    final Sprite[][] matrix = new Sprite[100][100];

    public ArrayList<LevelElement> collisionables;
    public Level(Texture texture) {
        collisionables = new ArrayList<>();
        Texture wallTexture = TextureManager.getInstance().getWall();
        Texture healthTexture = TextureManager.getInstance().getHealthPlace();

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                matrix[i][j] = new Sprite(texture);
                matrix[i][j].setPosition(i * 64, j * 64);
            }
        }
        //added health places
        collisionables.add(new LevelHelth(healthTexture,150,150));
        collisionables.add(new LevelHelth(healthTexture,2900,2900));

        //add some walls
        collisionables.add(new LevelWall(wallTexture, 300,600));
        collisionables.add(new LevelWall(wallTexture, 364,600));
        collisionables.add(new LevelWall(wallTexture, 428,600));
        collisionables.add(new LevelWall(wallTexture, 500,600));
        collisionables.add(new LevelWall(wallTexture, 564,600));
        collisionables.add(new LevelWall(wallTexture, 628,600));
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                matrix[i][j].draw(batch);

            }
        }

        collisionables.forEach(x -> x.render(batch));
    }
}
