package playerdata.characters;

import core.Constants;
import entities.units.player.Player;
import org.newdawn.slick.Image;
import playerdata.PlayerData;

import java.io.Serializable;

public abstract sealed class PlayableCharacter extends PlayerData implements Serializable permits Sigur, Phaedra {

    // PlayableCharacter is a data class. It will only be used to store characters' playerdata and will only be instantiated once per character.
    protected int level, exp, maxExp;

    protected int health;
    protected int attack;
    protected int defense;
    protected int critRate;
    protected int critDamage;
    protected int eAttack;
    protected int eDefense;

    protected Image sprite;

    protected Player entity;

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }


    protected PlayableCharacter() {
        // Default character constructor
        health = 1;
        attack = 1;
        this.level = 1;
        this.exp = 0;
        this.maxExp = Constants.LevelingConstants.MAX_EXP(1);
    }

    protected PlayableCharacter(int level, int exp) {
        this();
        // Default character constructor
        this.level = level;
        this.exp = exp;
         this.maxExp = Constants.LevelingConstants.MAX_EXP(level);
    }

    public void gainExp(int amount)   {
        this.exp+= amount;

    }







    public Player getEntity() {
        return entity;
    }

    public void setEntity(Player entity) {
        this.entity = entity;
    }

    public int getAttack() {
        return  attack;
    }
}
