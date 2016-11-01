package com.yerimen.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.yerimen.players.CharacterStatus;

public class TextureManager {

    private static TextureManager instance = null;

    private Texture wizard;
    private PlayerTexture wizardTexture;
    private Texture werewolf;
    private PlayerTexture werewolfTexture;
    private Texture vampire;
    private PlayerTexture vampireTexture;
    private CharacterStatus wizardStatus;
    private CharacterStatus werewolfStatus;
    private CharacterStatus vampireStatus;
    private Texture fireBall;
    private TiledMap map;
    private TiledMapRenderer mapRenderer;

    private TextureManager(){
        initializePlayerTextures();
        initializePowers();
        initializeMapAndMapRenderer();
    }
    private  void initializeMapAndMapRenderer(){
        map = new TmxMapLoader().load("maps/level01.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map,1/2f);
    }

    private void initializePowers() {
        fireBall = new Texture("images/fireball.png");
    }

    private void initializePlayerTextures() {
        wizard = new Texture("images/wizard.png");
        wizardTexture = new WizardTexture(wizard, 78);
        wizardStatus = new CharacterStatus();
        werewolf = new Texture("images/werewolf.png");
        werewolfTexture = new WerewolfTexture(werewolf, 80);
        werewolfStatus = new CharacterStatus();
        vampire = new Texture("images/vamp.png");
        vampireTexture = new VampireTexture(vampire, 48);
        vampireStatus = new CharacterStatus();
    }

    public static TextureManager getInstance() {
        if(instance == null) {
            instance = new TextureManager();
        }
        return instance;
    }

    public TiledMap getMap() {
        return map;
    }

    public TiledMapRenderer getMapRenderer() {
        return mapRenderer;
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

    public CharacterStatus getWizardStatus() {
        return wizardStatus;
    }

    public CharacterStatus getWerewolfStatus() {
        return werewolfStatus;
    }

    public CharacterStatus getVampireStatus() {
        return vampireStatus;
    }

    public Texture getFireBall() { return fireBall; }
}
