package com.yerimen.textures;

import com.badlogic.gdx.graphics.Texture;
import com.yerimen.players.CharacterStatus;

public class TextureManager {

    private static TextureManager instance = null;

    private Texture wizard;
    private PlayerTexture wizardTexture;
    private Texture werewolf;
    private PlayerTexture werewolfTexture;
    private Texture vampire;
    private PlayerTexture vampireTexture;
    private Texture rockman;
    private PlayerTexture rockmanTexture;
    private CharacterStatus wizardStatus;
    private CharacterStatus werewolfStatus;
    private CharacterStatus vampireStatus;
    private CharacterStatus rockmanStatus;
    private Texture fireBall;
    private Texture grass;
    private Texture wall;
    private Texture hospital;
    private Texture towerBlue;
    private Texture towerRed;
    private Texture health;
    private Texture healthContainer;
    private Texture iceBall;
    private Texture connectionBackground;

    private TextureManager(){
        initializePlayerTextures();
        initializePowers();
        initializeBars();
        initializeLevelTextures();
        connectionBackground = new Texture("images/loading.png");
    }

    private void initializePowers() {
        fireBall = new Texture("images/fireball.png");
        iceBall = new Texture("images/iceBall.png");
    }

    private void initializePlayerTextures() {
        wizard = new Texture("images/bulk.png");
        wizardTexture = new WizardTexture(wizard, 80);
        wizardStatus = new CharacterStatus();
        werewolf = new Texture("images/werewolf.png");
        werewolfTexture = new WerewolfTexture(werewolf, 80);
        werewolfStatus = new CharacterStatus();
        vampire = new Texture("images/rat.png");
        vampireTexture = new VampireTexture(vampire, 80);
        vampireStatus = new CharacterStatus();


        rockman = new Texture("images/rockman.png");
        rockmanTexture = new VampireTexture(rockman, 80);
        rockmanStatus = new CharacterStatus();
    }

    private void initializeBars() {
        health = new Texture("images/healthbar.png");
        healthContainer = new Texture("images/healthbarContainer.png");
    }

    private void initializeLevelTextures(){
        grass = new Texture("images/grass.jpg");
        wall = new Texture("images/wall.png");
        hospital = new Texture("images/health.png");
        towerBlue = new Texture("images/tower blue.png");
        towerRed = new Texture("images/tower red.png");
    }

    public static TextureManager getInstance() {
        if(instance == null) {
            instance = new TextureManager();
        }
        return instance;
    }

    public Texture getWizard() {
        return wizard;
    }

    public Texture getWerewolf() {
        return werewolf;
    }

    public Texture getVampire() {
        return vampire;
    }

    public PlayerTexture getWizardTexture() {
        return wizardTexture;
    }

    public PlayerTexture getWerewolfTexture() {
        return werewolfTexture;
    }

    public PlayerTexture getVampireTexture() {
        return vampireTexture;
    }

    public PlayerTexture getRockmanTexture() {
        return rockmanTexture;
    }

    public CharacterStatus getWizardStatus() {
        return wizardStatus;
    }

    public CharacterStatus getWerewolfStatus() {
        return werewolfStatus;
    }

    public CharacterStatus getVampireStatus() {
        return vampireStatus;
    }

    public CharacterStatus getRockmanStatus() {
        return rockmanStatus;
    }

    public Texture getFireBall() { return fireBall; }

    public Texture getGrass() {
        return grass;
    }
    public Texture getWall() {
        return wall;
    }
    public Texture getHospital() {
        return hospital;
    }
    public Texture getTowerBlue() {
        return towerBlue;
    }
    public Texture getTowerRed() {
        return towerRed;
    }

    public Texture getHealth() {
        return health;
    }

    public Texture getHealthContainer() {
        return healthContainer;
    }

    public Texture getIceBall() { return iceBall; }


    public Texture getConnectionBackground() {
        return connectionBackground;
    }
}
