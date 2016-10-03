package com.yerimen.players;

public class PlayerStatus {

    private Double hp = 100d;
    private Double mana = 100d;
    private Integer movSpeed = 10;
    private Integer atackSpeed = 10;
    private Integer ap = 10;
    private Integer ad = 10;
    private Integer armor = 10;
    private Integer magicResistance = 10;

    public Double getHp() {
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
}
