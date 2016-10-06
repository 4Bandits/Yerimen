package com.yerimen.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level {

    private Texture texture;
    final Sprite[][] matrix = new Sprite[100][100];

    public Level(Texture texture) {
        this.texture = texture;
        int x = -50;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                matrix[i][j] = new Sprite(texture);;
                matrix[i][j].setPosition((x + i) * 64, (x + j) * 64);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                matrix[i][j].draw(batch);
            }
        }
    }
}
