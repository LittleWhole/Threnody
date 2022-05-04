package playerdata;

import combat.artes.Arte;
import core.Constants;
import entities.units.player.PlayerOld;
import org.newdawn.slick.Image;

import java.io.Serializable;
import java.util.ArrayList;

public abstract strictfp sealed class PlayableCharacter extends PlayerData implements Serializable permits Sigur, Phaedra {

    // PlayableCharacter is a data class. It will only be used to store characters' playerdata and will only be instantiated once per character.
    protected int level, exp, maxExp;

    protected int health;
    protected int attack;
    protected int defense;
    protected int critRate;
    protected int critDamage;
    protected int eAttack;
    protected int eDefense;
    protected int affinity;

    protected Image sprite;
    protected ArrayList<Arte> artes;
    protected PlayerOld entity;

    protected PlayableCharacter() {
        // Default character constructor
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

    public PlayerOld getEntity() {
        return entity;
    }

    public void setEntity(PlayerOld entity) {
        this.entity = entity;
    }

}
