package com.yerimen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.yerimen.screens.game.GameContent;
import com.yerimen.screens.mainMenu.MainMenuScreen;
import com.yerimen.server.Server;
import com.yerimen.textures.TextureManager;
import com.yerimen.user.UserInformation;

public class YerimenGame extends Game {

	private SpriteBatch batch;
	private Server server;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create () {
        TextureManager.getInstance();
		this.batch = new SpriteBatch();
		this.shapeRenderer = new ShapeRenderer();
		this.server = new Server();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch(){
	    return this.batch;
    }

    public ShapeRenderer getShapeRenderer(){return this.shapeRenderer;}

    public void setProjectionMatrix(Matrix4 matrix){
        this.batch.setProjectionMatrix(matrix);
    }

	public void attemptConnectionTo(String serverUrl, UserInformation userInformation) {
		this.server.attemptConnectionTo(serverUrl, userInformation);
	}

	public void connect(GameContent gameContent) {
		this.server.connect(gameContent);
	}
}
