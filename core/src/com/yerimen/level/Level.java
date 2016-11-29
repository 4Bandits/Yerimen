package com.yerimen.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yerimen.textures.TextureManager;

import java.util.ArrayList;
import java.util.Random;

public class Level {

    final Sprite[][] matrix = new Sprite[100][100];

    public ArrayList<LevelElement> collisionables;
    public Level(Texture texture) {
        collisionables = new ArrayList<>();
        Texture rock = TextureManager.getInstance().getRock();
        Texture healthTexture = TextureManager.getInstance().getHealthPlace();

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                matrix[i][j] = new Sprite(texture);
                matrix[i][j].setPosition(i * 64, j * 64);
            }
        }

      //  for (int i = 0; i < 10; i++){
            
          //  matrix[10][10] = new Sprite(health);
           // matrix[10][10].setPosition(10* 64, 10* 64);

            collisionables.add(new LevelHelth(healthTexture,150,150));
//        }

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
