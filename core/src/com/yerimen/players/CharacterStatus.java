package com.yerimen.players;

import com.yerimen.powers.FireBall;
import com.yerimen.powers.Power;

import java.util.Arrays;
import java.util.List;

public class CharacterStatus {

    private int hp = 100;
    private Double mana = 100d;
    private Integer movSpeed = 10;
    private Integer atackSpeed = 10;
    private Integer ap = 10;
    private Integer ad = 10;
    private Integer armor = 10;
    private Integer magicResistance = 10;
    private List<Class<? extends Power>> powers = Arrays.asList(FireBall.class);

    public int getHp() {
        return hp;
    }

    public Double getMana() {
        return mana;
    }

    public Integer getMovSpeed() {
        return movSpeed;
    }

    public Integer getAtackSpeed() {
        return atackSpeed;
    }

    public Integer getAp() {
        return ap;
    }

    public Integer getAd() {
        return ad;
    }

    public Integer getArmor() {
        return armor;
    }

    public Integer getMagicResistance() {
        return magicResistance;
    }

    public int subtractHp(int value) {
        return hp -= value;
    }

    public CharacterStatus increaseHp(int value) {
        hp += value;
        return this;
    }

    public void respawnd() {
        hp = 100;
    }

}
