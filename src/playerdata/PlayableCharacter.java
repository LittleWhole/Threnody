package playerdata;

import combat.artes.Arte;
import core.Constants;
import core.Main;
import entities.units.Unit;
import entities.units.player.Player;
import entities.units.player.PlayerOld;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import util.DrawUtilities;

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
    // final ArrayList<Arte> dRINGU = new ArrayList<Arte>();

    protected Image sprite;

    protected Player entity;
    private playerState state;

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
