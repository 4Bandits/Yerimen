package com.yerimen.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.yerimen.players.Player;
import com.yerimen.screens.GameStateManager;
import com.yerimen.screens.State;
import com.yerimen.server.Server;
import com.yerimen.textures.TextureManager;
import com.yerimen.user.UserInformation;

public class ConnectionScreen extends State {
    private Server server;
    private Texture background;
    private Player player;

    public ConnectionScreen(GameStateManager gsm, String serverUrl, UserInformation userInformation) {
        super(gsm);
        this.server = new Server(serverUrl, userInformation, this);
        this.background = TextureManager.getInstance().getConnectionBackground();
       // this.server.connect();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float delta) {
        if (player != null) {
            gsm.set(new GameScreen(gsm, server, player));
        }
    }

    public void SetPlayer(Player player){
        this.player = player;
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(this.background, 0, 0);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
