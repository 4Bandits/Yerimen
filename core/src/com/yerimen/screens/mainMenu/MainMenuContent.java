package com.yerimen.screens.mainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.yerimen.screens.ScreenManager;
import com.yerimen.screens.YerimenScreen;
import com.yerimen.screens.game.ConnectionScreen;
import com.yerimen.user.UserInformation;

public class MainMenuContent {

    private Skin skin;
    private ScreenManager gameScreenManager;
    private Table table;
    private Label usernameLabel;
    private TextField usernameInput;
    private Label serverUrlLabel;
    private TextField serverUrlInput;
    private Label characterLabel;
    private CheckBox vampireOption;
    private CheckBox werewolfOption;
    private CheckBox wizardOption;
    private CheckBox rockmanOption;
    private TextButton startGameButton;
    private ButtonGroup characterOptions;
    private Label serverErrorMessage;

    public MainMenuContent(ScreenManager gameScreenManager) {
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.gameScreenManager = gameScreenManager;

        this.initializeTable();
        this.initializeGameButton();
        this.initializeUsernameInput();
        this.initializeServerUrlInput();
        this.initializeCharacterSelection();

        this.usernameLabel = new Label("Username", this.skin);
        this.serverUrlLabel = new Label("Server URL", this.skin);
        this.characterLabel = new Label("Select your character", this.skin);
        this.serverErrorMessage = new Label("Connection to the Server failed. Check the URL and try again.", this.skin);

        this.drawInTable();
    }

    public Table getContent() {
        return this.table;
    }

    private void drawInTable() {
        this.table.add(this.usernameLabel).uniform().colspan(4);
        this.table.row().space(10);
        this.table.add(this.usernameInput).width(100).uniform().colspan(4);
        this.table.row().space(10);
        this.table.add(this.serverUrlLabel).uniform().colspan(4);
        this.table.row().space(10);
        this.table.add(this.serverUrlInput).width(200).uniform().colspan(4);
        this.table.row().space(10);
        this.table.add(this.characterLabel).uniform().colspan(4);
        this.table.row().space(10);
        this.table.add(this.werewolfOption);
        this.table.add(this.vampireOption);
        this.table.add(this.wizardOption);
        this.table.add(this.rockmanOption);
        this.table.row().space(50);
        this.table.add(this.startGameButton).width(200).colspan(4);
    }

    private void initializeTable() {
        this.table = new Table();
        this.table.setFillParent(true);
    }

    private void initializeGameButton() {
        this.startGameButton = new TextButton("Connect!", this.skin);
        this.startGameButton.setDisabled(true);
        this.startGameButton.addListener(startGameButtonListener());
    }

    private void initializeCharacterSelection() {
        this.werewolfOption = new CheckBox("Werewolf", this.skin);
        this.vampireOption = new CheckBox("Vampire", this.skin);
        this.wizardOption = new CheckBox("Wizard", this.skin);
        this.rockmanOption = new CheckBox("Rockman", this.skin);
        this.characterOptions = new ButtonGroup(this.vampireOption, this.werewolfOption, this.wizardOption, this.rockmanOption);
        this.characterOptions.setChecked("Werewolf");
    }

    private void initializeUsernameInput() {
        this.usernameInput = new TextField("", this.skin);
        this.usernameInput.addListener(inputListener());
    }

    private void initializeServerUrlInput() {
        this.serverUrlInput = new TextField("http://localhost:9000", this.skin);
        this.serverUrlInput.addListener(inputListener());
    }

    private ChangeListener inputListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(usernameIsEmpty() || serverUrlIsEmpty()) {
                    startGameButton.setDisabled(true);
                } else {
                    startGameButton.setDisabled(false);
                }
            }
        };
    }

    private ChangeListener startGameButtonListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                UserInformation userInformation = new UserInformation(usernameToUse(), characterToUse());
                try {
                    gameScreenManager.set(new ConnectionScreen(gameScreenManager, serverUrlToConnect(), userInformation));
                } catch (RuntimeException exception) {
                    table.row().space(10);
                    table.add(serverErrorMessage).uniform().colspan(3);
                    serverUrlInput.setText("http://");
                }
            }
        };
    }

    private String usernameToUse() {
        return this.usernameInput.getText();
    }

    private String serverUrlToConnect() {
        return this.serverUrlInput.getText();
    }

    private boolean usernameIsEmpty() {
        return this.usernameInput.getText().equals("");
    }

    private boolean serverUrlIsEmpty() {
        return this.serverUrlInput.getText().equals("");
    }

    private String characterToUse() {
        CheckBox selectedCharacter = (CheckBox) characterOptions.getChecked();
        return selectedCharacter.getText().toString();
    }
}
