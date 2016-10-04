package com.yerimen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.yerimen.screen.YerimenScreen;
import com.yerimen.textures.TextureManager;

public class YerimenGame extends Game {

	public SpriteBatch batch;

	@Override
	public void create () {
        TextureManager.getInstance();
		batch = new SpriteBatch();
		setScreen(new YerimenScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch(){
	    return this.batch;
    }

    public void setProjectionMatrix(Matrix4 matrix){
        this.batch.setProjectionMatrix(matrix);
    }

}