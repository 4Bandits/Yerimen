package com.yerimen.screens.game.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SkillBar {

    private Skill fireSkill;
    private Skill iceSkill;

    public SkillBar() {
        this.fireSkill = new Skill(150, 50, "[1] Fire");
        this.iceSkill = new Skill(250, 50, "[2] Ice");

        // Initial Skill Bar configuration.
        this.fireSkill.show();
        this.iceSkill.hide();
    }

    public void render(SpriteBatch spriteBatch) {
        this.fireSkill.render(spriteBatch);
        this.iceSkill.render(spriteBatch);
    }

    public void changeSkill(String newSkill) {
        if(newSkill.equals("Fire")) {
            this.fireSkill.show();
            this.iceSkill.hide();
        } else {
            this.iceSkill.show();
            this.fireSkill.hide();
        }
    }
}

