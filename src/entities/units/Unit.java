package entities.units;

import combat.artes.Arte;
import combat.artes.ElementType;
import entities.core.Coordinate;
import entities.core.Entity;
import entities.core.EntityType;
import entities.units.player.Player;
import gamestates.BattleState;
import graphics.ui.combat.DamageNumber;
import org.newdawn.slick.Graphics;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static combat.artes.ElementType.*;

public class Unit extends Entity {

    // Abbreviations: LVL, EXP, HP, ATK, DEF, CR, CD, EATK, EDEF, AFF
    protected int level;
    protected int health;
    protected int attack;
    protected int defense;
    protected int critRate;
    protected int critDamage;
    protected int eAttack;
    protected int eDefense;
    protected Map<ElementType, Integer> eAffinity;

    protected List<Arte<? extends Unit>> arteDeck;
    protected List<Arte<? extends Unit>> arteHand;
    protected Queue<Arte<? extends Unit>> arteQueue;
    protected Arte<? extends Unit> move;

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

        this.eAffinity = new EnumMap<>(ElementType.class);
        eAffinity.putAll(Map.of(FIRE, 0, WATER, 0, EARTH, 0, ICE, 0, WIND, 0, ELECTRIC, 0, LIGHT, 0, DARK, 0, POISON, 0));
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

    public void takeDamage(int amount)  {
        System.out.println(position.toString());
        BattleState.damageNumbers.add(new DamageNumber(amount, hitBox.getCenterX(), hitBox.getCenterY()));
        this.health-=amount;
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

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Queue<Arte<? extends Unit>> getArteQueue() {
        return arteQueue;
    }
}
