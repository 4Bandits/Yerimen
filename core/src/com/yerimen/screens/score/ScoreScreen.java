package com.yerimen.screens.score;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.yerimen.screens.ScreenManager;
import com.yerimen.screens.YerimenScreen;
import com.yerimen.screens.game.GameContent;

public class ScoreScreen extends YerimenScreen{

    private ScoreContent content;
    private Stage stage;

    public ScoreScreen(GameContent gameContent,ScreenManager gsm) {
        super(gsm);
        this.content = new ScoreContent(gameContent);
        this.stage = new Stage();
        initializeCamera();
        Gdx.input.setInputProcessor(stage);
        this.stage.addActor(this.content.getContent());
    }
    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act();
        this.stage.draw();
    }

    private void initializeCamera(){
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.update();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float delta) {
        if(!Gdx.input.isKeyPressed(Input.Keys.TAB)){
            gsm.pop();
            //gsm.push(new ScoreScreen(gameContent,gsm));
        }

    }


    @Override
    public void dispose() {

    }
}
