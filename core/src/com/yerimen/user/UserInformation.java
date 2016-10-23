package com.yerimen.user;

import com.yerimen.players.CharacterStatus;
import com.yerimen.textures.PlayerTexture;
import com.yerimen.textures.TextureManager;

public class UserInformation {

    private final String username;
    private final String character;

    public UserInformation(String username, String character) {
        this.username = username;
        this.character = character;
    }

    public String getUsername() {
        return username;
    }

    public PlayerTexture getPlayerTexture() {
        switch (this.character) {
            case "Werewolf":
                return TextureManager.getInstance().getWerewolfTexture();
            case "Vampire":
                return TextureManager.getInstance().getVampireTexture();
            case "Wizard":
                return TextureManager.getInstance().getWizardTexture();
        }
        return null;
    }

    public CharacterStatus getPlayerTextureStatus() {
        switch (this.character) {
            case "Werewolf":
                return TextureManager.getInstance().getWerewolfStatus();
            case "Vampire":
                return TextureManager.getInstance().getVampireStatus();
            case "Wizard":
                return TextureManager.getInstance().getWizardStatus();
        }
        return null;
    }
}
