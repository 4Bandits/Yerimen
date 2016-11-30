package com.yerimen.screens.score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.yerimen.YerimenGame;
import com.yerimen.players.Player;
import com.yerimen.screens.YerimenScreen;
import com.yerimen.screens.game.GameContent;


public class ScoreContent {

    GameContent gameContent;
    private Table table;
    Skin skin ;

    public ScoreContent(GameContent content){
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        gameContent=content;
        this.table = new Table();
        table.setHeight(800);
        table.setWidth(1200);
        table.center();
        table.setTransform(true);
        //works
        //table.addAction(Actions.scaleTo(2.3f, 2.5f, 2f)));
        //does not work
        table.addAction(Actions.alpha(0.2f, 2f));
        //does not work either
        table.addAction(Actions.color(new Color(1f,1f,1f, 0.2f), 2f));


        //this.table.setFillParent(true);
    }
    public void drawTable(){
        Player mainPlayer=gameContent.getMainPlayer();
        Label mainLabel = new Label(gameContent.getUsernameById(mainPlayer.getId())+"   "+gameContent.killsFor(mainPlayer)+" / "+gameContent.deathsFor(mainPlayer),skin);
        this.table.add(mainLabel).uniform().colspan(4);
        this.table.row().space(10);
        gameContent.getEnemies().forEach(p ->{
            Label pLabel = new Label(gameContent.getUsernameById(p.getId())+"   "+gameContent.killsFor(p)+" / "+gameContent.deathsFor(p),skin);
            this.table.add(pLabel).uniform().colspan(4);
            this.table.row().space(10);
        });
    }
    public Table getContent() {
        this.drawTable();
        return this.table;
    }


}
