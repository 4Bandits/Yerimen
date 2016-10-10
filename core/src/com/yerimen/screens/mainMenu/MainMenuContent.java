package com.yerimen.screens.mainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.yerimen.YerimenGame;
import com.yerimen.screens.game.YerimenScreen;
import com.yerimen.user.UserInformation;

public class MainMenuContent {

    private Table table;
    private Label usernameLabel;
    private TextField usernameInput;
    private Label characterLabel;
    private CheckBox vampireOption;
    private CheckBox werewolfOption;
    private CheckBox wizardOption;
    private TextButton startGameButton;
    private ButtonGroup characterOptions;

    public MainMenuContent(YerimenGame game) {
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        this.initializeTable();
        this.initializeGameButton(game, skin);
        this.initializeUsernameInput(skin);
        this.initializeCharacterSelection(skin);
        this.usernameLabel = new Label("Username", skin);
        this.characterLabel = new Label("Select your character", skin);

        this.drawInTable();
    }

    public Table getContent() {
        return this.table;
    }

    private void drawInTable() {
        this.table.add(this.usernameLabel).uniform().colspan(3);
        this.table.row().space(10);
        this.table.add(this.usernameInput).width(100).uniform().colspan(3);
        this.table.row().space(10);
        this.table.add(this.characterLabel).uniform().colspan(3);
        this.table.row().space(10);
        this.table.add(this.werewolfOption);
        this.table.add(this.vampireOption);
        this.table.add(this.wizardOption);
        this.table.row().space(50);
        this.table.add(this.startGameButton).width(200).colspan(3);
    }

    private void initializeTable() {
        this.table = new Table();
        this.table.setFillParent(true);
    }

    private void initializeGameButton(YerimenGame game, Skin skin) {
        this.startGameButton = new TextButton("Connect!", skin);
        this.startGameButton.setDisabled(true);
        this.startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                UserInformation userInformation = new UserInformation(usernameToUse(), characterToUse());
                game.setScreen(new YerimenScreen(game, userInformation));
            }
        });
    }

    private void initializeCharacterSelection(Skin skin) {
        this.werewolfOption = new CheckBox("Werewolf", skin);
        this.vampireOption = new CheckBox("Vampire", skin);
        this.wizardOption = new CheckBox("Wizard", skin);
        this.characterOptions = new ButtonGroup(this.vampireOption, this.werewolfOption, this.wizardOption);
        this.characterOptions.setChecked("Werewolf");
    }

    private void initializeUsernameInput(Skin skin) {
        this.usernameInput = new TextField("", skin);
        this.usernameInput.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(usernameIsEmpty()) {
                    startGameButton.setDisabled(true);
                } else {
                    startGameButton.setDisabled(false);
                }
            }
        });
    }

    private String usernameToUse() {
        return this.usernameInput.getText();
    }

    private boolean usernameIsEmpty() {
        return this.usernameInput.getText().equals("");
    }

    private String characterToUse() {
        CheckBox selectedCharacter = (CheckBox) characterOptions.getChecked();
        return selectedCharacter.getText().toString();
    }
}
