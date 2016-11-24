package com.yerimen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.yerimen.screens.ScreenManager;
import com.yerimen.screens.game.GameContent;
import com.yerimen.screens.mainMenu.MainMenuScreen;
import com.yerimen.textures.TextureManager;
import com.yerimen.user.UserInformation;

public class YerimenGame extends Game {

	private SpriteBatch batch;
	private SpriteBatch hudBatch;
	private ShapeRenderer shapeRenderer;
	private ScreenManager gsm;

	@Override
	public void create () {
        TextureManager.getInstance();
		this.batch = new SpriteBatch();
		this.hudBatch = new SpriteBatch();
		this.shapeRenderer = new ShapeRenderer();
		gsm = new ScreenManager();
		gsm.push(new MainMenuScreen(gsm));
		//this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch, shapeRenderer);
		//super.render();
	}

	public SpriteBatch getBatch(){
	    return this.batch;
    }

    public SpriteBatch getHudBatch() {
    	return this.hudBatch;
	}

    public ShapeRenderer getShapeRenderer(){return this.shapeRenderer;}

    public void setProjectionMatrix(Matrix4 matrix){
        this.batch.setProjectionMatrix(matrix);
    }

	public void attemptConnectionTo(String serverUrl, UserInformation userInformation) {
		//this.server.attemptConnectionTo(serverUrl, userInformation);
	}

	public void connect(GameContent gameContent) {
		//this.server.connect(gameContent);
	}
}
