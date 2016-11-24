package com.yerimen.bars;

import com.yerimen.textures.TextureManager;


public class HealthBar extends Bar {

    public HealthBar(float xPosition, float yPosition, int heigth, int width) {
        super(TextureManager.getInstance().getHealth(), TextureManager.getInstance().getHealthContainer(), xPosition, yPosition + 70, heigth, width);
    }

}