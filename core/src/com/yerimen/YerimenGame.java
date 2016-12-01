package com.yerimen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.yerimen.screens.ScreenManager;
import com.yerimen.screens.mainMenu.MainMenuScreen;
import com.yerimen.sounds.SoundManager;
import com.yerimen.textures.TextureManager;

public class YerimenGame extends Game {

	private SpriteBatch batch;
	private SpriteBatch hudBatch;
	private ShapeRenderer shapeRenderer;
	private ScreenManager gameScreenManager;

	@Override
	public void create () {
        TextureManager.getInstance();
		SoundManager.getInstance();
		this.batch = new SpriteBatch();
		this.hudBatch = new SpriteBatch();
		this.shapeRenderer = new ShapeRenderer();
		this.gameScreenManager = new ScreenManager();

		this.gameScreenManager.push(new MainMenuScreen(this.gameScreenManager));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		this.gameScreenManager.update(Gdx.graphics.getDeltaTime());
		this.gameScreenManager.render(this.batch, this.hudBatch, this.shapeRenderer);
	}
}
