package com.yerimen.textures;

import com.badlogic.gdx.graphics.Texture;
import com.yerimen.players.PlayerStatus;

public class TextureManager {

    private static TextureManager instance = null;

    private Texture wizard;
    private PlayerTexture wizardTexture;
    private Texture werewolf;
    private PlayerTexture werewolfTexture;
    private Texture vampire;
    private PlayerTexture vampireTexture;
    private PlayerStatus wizardStatus;
    private PlayerStatus werewolfStatus;
    private PlayerStatus vampireStatus;


    protected TextureManager(){
        wizard = new Texture("images/wizard.png");
        wizardTexture = new WizardTexture(wizard, 78);
        wizardStatus = new PlayerStatus();
        werewolf = new Texture("images/werewolf.png");
        werewolfTexture = new WerewolfTexture(werewolf, 80);
        werewolfStatus = new PlayerStatus();
        vampire = new Texture("images/vamp.png");
        vampireTexture = new VampireTexture(vampire, 48);
        vampireStatus = new PlayerStatus();
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

    public PlayerStatus getWizardStatus() {
        return wizardStatus;
    }

    public PlayerStatus getWerewolfStatus() {
        return werewolfStatus;
    }

    public PlayerStatus getVampireStatus() {
        return vampireStatus;
    }
}
