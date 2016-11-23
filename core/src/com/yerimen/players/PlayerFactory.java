package com.yerimen.players;

import com.badlogic.gdx.math.Vector2;
import com.yerimen.textures.TextureManager;

public class PlayerFactory {

    public static Character getCharacter(String characterID, Vector2 position, String characterSelected) {
        switch (characterSelected) {
            case "Vampire": return new Character(characterID, TextureManager.getInstance().getVampireTexture(), new CharacterStatus(), position);
            case "Wizard": return new Character(characterID, TextureManager.getInstance().getWizardTexture(), new CharacterStatus(), position);
            case "Rockman": return new Character(characterID, TextureManager.getInstance().getRockmanTexture(), new CharacterStatus(), position);
            default: return new Character(characterID, TextureManager.getInstance().getWerewolfTexture(), new CharacterStatus(), position);
        }
    }
}
