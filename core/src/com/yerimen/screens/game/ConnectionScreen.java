package com.yerimen.screens.game;

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

    public ConnectionScreen(ScreenManager gameScreenManager, String serverUrl, UserInformation userInformation) {
        super(gameScreenManager);
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
            gameScreenManager.set(new GameScreen(gameScreenManager, server, player, enemies));
        }
    }

    public void setPlayer(Player player, HashMap<String, Character> players){
        this.enemies = players;
        this.player = player;
    }

    @Override
    public void render(SpriteBatch spriteBatch, SpriteBatch hudBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        spriteBatch.draw(this.background, 0, 0);
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }
}
