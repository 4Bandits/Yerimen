package com.yerimen.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yerimen.players.MainPlayer;
import com.yerimen.players.NullPlayer;
import com.yerimen.server.Server;

public class GameContent {

    private Server server;
    private MainPlayer mainPlayer;


    public GameContent() {
        this.mainPlayer= new NullPlayer();
        this.initializeServer();
    }

    public void update(float delta, OrthographicCamera camera) {
        mainPlayer.update(delta, camera);
    }

    public void render(SpriteBatch batch) {
        mainPlayer.render(batch);
        this.server.getPlayers().forEach(player -> player.render(batch));
    }

    private void initializeServer() {
        this.server= new Server(this);
    }

    public void setMainPlayer(MainPlayer mainPlayer){
        this.mainPlayer = mainPlayer;
    }

    public MainPlayer getMainPlayer() {return this.mainPlayer; }

}
