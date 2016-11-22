package com.yerimen.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.YerimenGame;
import com.yerimen.players.Player;
import com.yerimen.screens.GameStateManager;
import com.yerimen.screens.State;
import com.yerimen.server.Server;
import com.yerimen.user.UserInformation;

public class GameScreen extends State {

    private GameContent gameContent;
    private GameHud gameHud;
    private Server server;

    public GameScreen(GameStateManager gsm, Server server, Player player) {
        super(gsm);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        this.gameContent = new GameContent(player);
        this.gameHud = new GameHud();
        this.server = server;


        this.initializeCamera();
    }

    private void initializeCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
    }

/*
    public void render(float delta) {




       // this.ca game.setProjectionMatrix(camera.combined);
        this.update(delta);
        this.draw();
    }
*/
    @Override
    protected void handleInput() {

    }

    public void update(float delta) {
        this.gameContent.update(delta, camera);
        this.updateCamera();
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.updateCamera();
        sb.setProjectionMatrix(camera.combined);
        this.draw(sb, sr);
    }

    @Override
    public void dispose() {

    }

    private void draw(SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        this.gameContent.render(sb, sr);
        this.gameHud.render(sb);
        sb.end();
    }

    private void updateCamera() {
        Vector2 vector2 = this.gameContent.getMainPlayer().getPosition();
        float x = Math.min(Math.max(vector2.x, 960), 2240);
        float y = Math.min(Math.max(vector2.y, 550), 2670);
        camera.position.set(x, y, 0);
        camera.update();
    }

}
