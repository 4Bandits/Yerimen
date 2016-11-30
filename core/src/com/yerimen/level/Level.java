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
        Texture healthTexture = TextureManager.getInstance().getHospital();

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                matrix[i][j] = new Sprite(texture);
                matrix[i][j].setPosition(i * 64, j * 64);
            }
        }
        //added health places
        collisionables.add(new Hospital(healthTexture, 200, 100));
        collisionables.add(new Hospital(healthTexture, 2900, 3000));

        //add some walls
        for (int i = 0; i < 10; i++) {
            if(i % 2 == 1){
                int x = i * 100;
                collisionables.add(new Wall(wallTexture, x, 600));
                collisionables.add(new Wall(wallTexture, x + 64, 600));
                collisionables.add(new Wall(wallTexture, x + 128, 600));
            }
        }

        for (int i = 0; i < 10; i++) {
            if(i % 2 == 1){
                int x = (i * 100) + 2000 ;
                collisionables.add(new Wall(wallTexture, x, 2400));
                collisionables.add(new Wall(wallTexture, x + 64, 2400));
                collisionables.add(new Wall(wallTexture, x + 128, 2400));
            }
        }

        collisionables.add(new Tower(TextureManager.getInstance().getTowerBlue(),500,200));
        collisionables.add(new Tower(TextureManager.getInstance().getTowerRed(),2500,2800));

      /*  collisionables.add(new Wall(wallTexture, 100,600));
        collisionables.add(new Wall(wallTexture, 164,600));
        collisionables.add(new Wall(wallTexture, 228,600));
        collisionables.add(new Wall(wallTexture, 300,600));
        collisionables.add(new Wall(wallTexture, 364,600));
        collisionables.add(new Wall(wallTexture, 428,600));*/
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
