package playerdata.characters;

import core.Constants;
import core.Main;
import entities.units.player.Player;
import org.newdawn.slick.Image;
import playerdata.PlayerData;

import java.io.Serializable;

public abstract sealed class PlayableCharacter extends PlayerData implements Serializable permits Sigur, Phaedra {

    // PlayableCharacter is a data class. It will only be used to store characters' playerdata and will only be instantiated once per character.
    protected int level, exp, maxExp;

    protected int health;

    public int getMaxExp() {
        return maxExp;
    }

    public int getHealth() {
        return health;
    }

    public int getDefense() {
        return defense;
    }

    public double getCritRate() {
        return critRate;
    }

    public int getCritDamage() {
        return critDamage;
    }

    public int geteAttack() {
        return eAttack;
    }

    public int geteDefense() {
        return eDefense;
    }

    protected int attack;
    protected int defense;
    protected double critRate;
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
        this.level = 1;
        this.health = 50 + 25 * (int)(Math.log(level*2));
        this.attack = 10 + 5 *  (int)(Math.log(level*2));
        this.defense = 5 + 3 *  (int)(Math.log(level*2));
        this.critDamage = 5 + 2 * (int)(Math.log(level*2));
        this.critRate = .05 + 0.5 * (int)(Math.log(level*2));
        this.eDefense = 3 + 2 * (int)(Math.log(level*2));
        this.eAttack = 5 + 2 * (int)(Math.log(level*2));
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
        Main.stats.exp += amount;
        while(Main.stats.exp >= Main.stats.maxExp) {
            System.out.println("LEVEL UP!");
            levelUp();
            Main.stats.exp -=this.maxExp;
            //this.exp-=this.maxExp;
            Main.stats.maxExp = Constants.LevelingConstants.MAX_EXP(this.level);
        }

    }

    protected void levelUp() {
        Main.stats.level++;
        this.level++;
        updateStats();
    }

    protected void updateStats() {
        this.health = 50 + 25 * (int)(Math.log(level*2));
        this.attack = 10 + 5 *  (int)(Math.log(level*2));
        this.defense = 5 + 3 *  (int)(Math.log(level*2));
        this.critDamage = 2 + (int) (Math.log(level * 2));
        this.critRate = .05 + 0.001 * (int)(Math.log(level*2));
        this.eDefense = 3 + 2 * (int)(Math.log(level*2));
        this.eAttack = 5 + 3 * (int)(Math.log(level*2));
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
