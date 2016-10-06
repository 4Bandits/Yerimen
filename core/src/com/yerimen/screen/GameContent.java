package com.yerimen.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yerimen.level.Level;
import com.yerimen.players.Player;
import com.yerimen.players.NullPlayer;
import com.yerimen.server.Server;
import com.yerimen.textures.TextureManager;

public class GameContent {

    private Server server;
    private Player mainPlayer;
    private Level level;

    public GameContent() {
        this.mainPlayer= new NullPlayer();
        this.initializeServer();
        this.level = new Level(TextureManager.getInstance().getGrass());
    }

    public void update(float delta, OrthographicCamera camera) {
        mainPlayer.update(delta, camera);
        this.server.getEnemies().forEach(enemy -> enemy.update(delta, camera));
        this.server.getPowers().forEach(power -> power.update(delta));
    }

    public void render(SpriteBatch batch) {
        this.level.render(batch);
        this.server.getEnemies().forEach(player -> player.render(batch));
        this.server.getPowers().forEach(power -> power.render(batch));
        mainPlayer.render(batch);
    }

    private void initializeServer() {
        this.server= new Server(this);
    }

    public void setMainPlayer(Player mainPlayer){
        this.mainPlayer = mainPlayer;
    }

    public Player getMainPlayer() {return this.mainPlayer; }

}
