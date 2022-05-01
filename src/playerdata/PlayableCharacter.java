package playerdata;

import combat.artes.Arte;
import core.Constants;
import entities.units.player.Player;
import org.newdawn.slick.Image;

import java.util.ArrayList;

public sealed class PlayableCharacter permits Sigur, Phaedra {

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
    protected Player entity;

    public PlayableCharacter() {
        // Default character constructor
        this.level = 1;
        this.exp = 0;
        this.maxExp = Constants.LevelingConstants.MAX_EXP(1);
    }

    public PlayableCharacter(int level, int exp) {
        this();
        // Default character constructor
        this.level = level;
        this.exp = exp;
        this.maxExp = Constants.LevelingConstants.MAX_EXP(level);
    }

    public Player getEntity() {
        return entity;
    }

    public void setEntity(Player entity) {
        this.entity = entity;
    }
}
