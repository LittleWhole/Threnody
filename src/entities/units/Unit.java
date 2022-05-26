package entities.units;

import combat.artes.Arte;
import combat.artes.ElementType;
import entities.core.Coordinate;
import entities.core.Entity;
import entities.core.EntityType;
import entities.units.player.Player;
import gamestates.BattleState;
import graphics.ui.combat.DamageNumber;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static combat.artes.ElementType.*;
import static core.Constants.CombatConstants.*;

@SuppressWarnings({"unchecked"})
public abstract class Unit<T extends Unit<?>> extends Entity {

    private final Random R = new Random();

    // Abbreviations: LVL, EXP, HP, ATK, DEF, CR, CD, EATK, EDEF, AFF
    protected int level;
    protected int health;
    protected int attack;
    protected int defense;
    protected double critRate;
    protected int critDamage;
    protected int eAttack;
    protected int eDefense;
    protected Map<ElementType, Integer> eAffinity;

    protected List<Arte<? extends Unit>> arteDeck;
    protected List<Arte<? extends Unit>> arteHand;
    protected Queue<Arte<? extends Unit>> arteQueue;
    protected Arte<? extends Unit> move;

    public Direction getNsDir() {
        return nsDir;
    }

    protected Direction nsDir;

    public Direction getEwDir() {
        return ewDir;
    }

    protected Direction ewDir;
    public int getMana() {
        return mana;
    }

    public void generateMana(int amt) {
        this.mana += amt;
    }

    protected int mana;

    public Unit() {
        super();
        this.type = EntityType.UNIT;
        this.mana = 1;
        this.level = 1;
        this.health = 1;
        this.attack = 1;
        this.defense = 1;
        this.critRate = 1;
        this.critDamage = 1;
        this.eAttack = 1;
        this.eDefense = 1;
        this.ewDir = Direction.randomEastWest();
        this.nsDir = Direction.randomNorthSouth();
        this.eAffinity = new EnumMap<>(ElementType.class);
        eAffinity.putAll(Map.of(PHYSICAL, 0, FIRE, 0, WATER, 0, EARTH, 0, ICE, 0, WIND, 0, ELECTRIC, 0, LIGHT, 0, DARK, 0));
    }

    @Override
    protected void renderOther(Graphics g) {

    }

    public Unit(int level, int health, int attack, int defense, int critRate, int critDamage, int eAttack, int eDefense) {
        super();
        this.level = level;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.critRate = critRate;
        this.critDamage = critDamage;
        this.eAttack = eAttack;
        this.eDefense = eDefense;
    }

    public void setPosition(int x, int y)   {
        this.position = new Coordinate(x,y);
    }
    public void setPosition(Coordinate c) {this.position = c;}

    public T takeDamage(int amount, ElementType element)  {
        double damage;
        System.out.println(position.toString());
        if (element == PHYSICAL) damage = (amount * DEFENSE_MULTIPLIER)/(DEFENSE_MULTIPLIER + defense);
        else damage = (amount * DEFENSE_MULTIPLIER)/(DEFENSE_MULTIPLIER + (defense * EDEF_DEF_DIVISOR) + eDefense);
        //if(amount>=health) BattleState.damageNumbers.add(new DamageNumber(amount, hitBox.getCenterX(), hitBox.getCenterY(), element.color));
        /*else*/ BattleState.damageNumbers.add(new DamageNumber((int) damage, hitBox.getCenterX(), hitBox.getCenterY(), element.color));
        health -= damage;
        return (T) this;
    }

    public void regenerate(int amount)    {
        this.health += amount;
    }

    public int getAttack() {
        return attack;
    }

    public Unit getUnit() {
        return this;
    }

    public int getHealth() {
        return health;
    }

    public T setHealth(int health) {
        this.health = health;
        return (T) this;
    }

    public T setAttack(int attack) {
        this.attack = attack;
        return (T) this;
    }

    public int calculateDamage(ElementType element) {
        return attack * (1 + eAffinity.get(element)/100) * (1 + (Math.random() <= critRate ? critDamage : 0));
    }

    public Queue<Arte<? extends Unit>> getArteQueue() {
        return arteQueue;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "level=" + level +
                ", health=" + health +
                ", attack=" + attack +
                ", defense=" + defense +
                ", critRate=" + critRate +
                ", critDamage=" + critDamage +
                ", eAttack=" + eAttack +
                ", eDefense=" + eDefense +
                ", eAffinity=" + eAffinity +
                ", arteDeck=" + arteDeck +
                ", arteHand=" + arteHand +
                ", arteQueue=" + arteQueue +
                ", move=" + move +
                ", mana=" + mana +
                '}';
    }
}
