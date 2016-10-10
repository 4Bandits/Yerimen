package com.yerimen.screens.mainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.yerimen.YerimenGame;
import com.yerimen.screens.game.GameContent;
import com.yerimen.screens.game.YerimenScreen;
import sun.font.GraphicComponent;

public class MainMenuScreen extends ScreenAdapter {

    private YerimenGame game;
    private OrthographicCamera camera;
    private Skin skin;
    private Stage stage;

    public MainMenuScreen(YerimenGame game) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        this.game = game;
        this.initializeCamera();

        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        TextButton button = new TextButton("Start game", this.skin);
        button.setDisabled(true);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new YerimenScreen(game));
            }
        });

        Label label = new Label("Username", skin);
        Label label2 = new Label("Select your character", skin);
        TextField input = new TextField("", skin);
        input.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!input.getText().equals("")) {
                    button.setDisabled(false);
                } else {
                    button.setDisabled(true);
                }
            }
        });

        Button b1 = new CheckBox("Vampire", skin);
        Button b2 = new CheckBox("Werewolf", skin);
        Button b3 = new CheckBox("Wizard", skin);
        ButtonGroup group = new ButtonGroup(b1, b2, b3);
        group.setChecked("Vampire1");
        group.getChecked();

        table.add(label).uniform().colspan(3);
        table.row().space(10);
        table.add(input).width(100).uniform().colspan(3);
        table.row().space(10);
        table.add(label2).uniform().colspan(3);
        table.row().space(10);
        table.add(b1);
        table.add(b2);
        table.add(b3);
        table.row().space(10);
        table.add(button).width(200).colspan(3);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    private void initializeCamera(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
    }

}
