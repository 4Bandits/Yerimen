package com.yerimen.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.yerimen.players.Character;
import com.yerimen.players.Player;
import com.yerimen.powers.Power;
import com.yerimen.screens.ScreenManager;
import com.yerimen.screens.YerimenScreen;
import com.yerimen.server.Server;
import com.yerimen.textures.TextureManager;
import com.yerimen.user.UserInformation;

import java.util.HashMap;
import java.util.List;

public class ConnectionScreen extends YerimenScreen {
    private Server server;
    private Texture background;
    private Player player;
    private HashMap<String, Character> enemies;
    private List<Power> powers;

    public ConnectionScreen(ScreenManager gsm, String serverUrl, UserInformation userInformation) {
        super(gsm);
        this.server = new Server(serverUrl, userInformation, this);
        this.background = TextureManager.getInstance().getConnectionBackground();
        this.server.notifyMyLogin();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float delta) {
        if (player != null) {
            gsm.set(new GameScreen(gsm, server, player, enemies));
        }
    }

    public void SetPlayer(Player player, HashMap<String, Character> players){
        this.enemies = players;
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
