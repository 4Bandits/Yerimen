package com.yerimen.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level {

    final Sprite[][] matrix = new Sprite[100][100];

    public Level(Texture texture) {
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                matrix[i][j] = new Sprite(texture);;
                matrix[i][j].setPosition(i * 64, j * 64);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                matrix[i][j].draw(batch);
            }
        }
    }
}
