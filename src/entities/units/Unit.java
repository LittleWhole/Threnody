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

    protected final Random R = new Random();

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


    protected int frame;
    protected int spriteY;//where sprite is on spritesheet
    protected List<Arte<? extends Unit>> arteDeck;
    protected List<Arte<? extends Unit>> arteHand;
    protected Queue<Arte<? extends Unit>> arteQueue;
    protected Arte<? extends Unit> move;
    protected Direction nsDir;
    protected Direction ewDir;
    protected int mana;
    protected int turnMana;
    protected int manaAdd;
    protected int queuedManaExtra;
    protected int queuedManaRemoval;

    public Unit() {
        super();
        this.type = EntityType.UNIT;
        this.mana = 1;
        this.turnMana = 1;
        this.queuedManaRemoval = 0;
        this.queuedManaExtra = 0;
        this.manaAdd = 0;
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
        eAffinity.putAll(Map.of(FIRE, 0, WATER, 0, EARTH, 0, ICE, 0, WIND, 0, ELECTRIC, 0, LIGHT, 0, DARK, 0, POISON, 0));
        this.frame = 0;
        this.spriteY = 0;
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

    public Direction getNsDir() {
        return nsDir;
    }

    public T setNsDir(Direction nsDir) {
        this.nsDir = nsDir;
        return (T) this;
    }

    public Direction getEwDir() {
        return ewDir;
    }

    public T setEwDir(Direction ewDir) {
        this.ewDir = ewDir;
        return (T) this;
    }

    public int getMana() {
        return mana;
    }

    public T setMana(int mana) {
        this.mana = mana;
        return (T) this;
    }

    public void generateMana(int amt) {
        this.mana += amt;
    }

    @Override
    protected void renderOther(Graphics g) {

    }

    public void setPosition(int x, int y) {
        this.position = new Coordinate(x, y);
    }

    public void setPosition(Coordinate c) {
        this.position = c;
    }

    public T takeDamage(int amount, ElementType element) {
        double damage;
        if (element == PHYSICAL) damage = (amount * DEFENSE_MULTIPLIER) / (DEFENSE_MULTIPLIER + defense);
        else damage = (amount * DEFENSE_MULTIPLIER) / (DEFENSE_MULTIPLIER + (defense * EDEF_DEF_DIVISOR) + eDefense);
        //if(amount>=health) BattleState.damageNumbers.add(new DamageNumber(amount, hitBox.getCenterX(), hitBox.getCenterY(), element.color));
        /*else*/
        BattleState.damageNumbers.add(new DamageNumber((int) Math.round(damage), hitBox.getCenterX() + R.nextInt(-50, 51), hitBox.getCenterY() + R.nextInt(-50, 51), element.color));
        health -= (int) Math.round(damage);
        return (T) this;
    }

    public void regenerate(int amount) {
        BattleState.damageNumbers.add(new DamageNumber(amount, hitBox.getCenterX(), hitBox.getCenterY(), new Color(171, 243, 45)).setOutlineColor(Color.white));
        this.health += amount;
    }

    public T addDefense(int amount) {
        this.defense += amount;
        BattleState.damageNumbers.add(new DamageNumber(amount, hitBox.getCenterX(), hitBox.getCenterY(), new Color(100, 100, 100)).setOutlineColor(Color.white));
        return (T) this;
    }

    public int getAttack() {
        return attack;
    }

    public T setAttack(int attack) {
        this.attack = attack;
        return (T) this;
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

    public int getLevel() {
        return level;
    }

    public T setLevel(int level) {
        this.level = level;
        return (T) this;
    }

    public int getDefense() {
        return defense;
    }

    public T setDefense(int defense) {
        this.defense = defense;
        return (T) this;
    }

    public double getCritRate() {
        return critRate;
    }

    public T setCritRate(double critRate) {
        this.critRate = critRate;
        return (T) this;
    }

    public int getCritDamage() {
        return critDamage;
    }

    public T setCritDamage(int critDamage) {
        this.critDamage = critDamage;
        return (T) this;
    }

    public int geteAttack() {
        return eAttack;
    }

    public T seteAttack(int eAttack) {
        this.eAttack = eAttack;
        return (T) this;
    }

    public int geteDefense() {
        return eDefense;
    }

    public T seteDefense(int eDefense) {
        this.eDefense = eDefense;
        return (T) this;
    }

    public Map<ElementType, Integer> geteAffinity() {
        return eAffinity;
    }

    public T seteAffinity(Map<ElementType, Integer> eAffinity) {
        this.eAffinity = eAffinity;
        return (T) this;
    }

    public List<Arte<? extends Unit>> getArteDeck() {
        return arteDeck;
    }

    public T setArteDeck(List<Arte<? extends Unit>> arteDeck) {
        this.arteDeck = arteDeck;
        return (T) this;
    }

    public List<Arte<? extends Unit>> getArteHand() {
        return arteHand;
    }

    public T setArteHand(List<Arte<? extends Unit>> arteHand) {
        this.arteHand = arteHand;
        return (T) this;
    }

    public Queue<Arte<? extends Unit>> getArteQueue() {
        return arteQueue;
    }

    public T setArteQueue(Queue<Arte<? extends Unit>> arteQueue) {
        this.arteQueue = arteQueue;
        return (T) this;
    }

    public Arte<? extends Unit> getMove() {
        return move;
    }

    public T setMove(Arte<? extends Unit> move) {
        this.move = move;
        return (T) this;
    }

    public int getTurnMana() {
        return turnMana;
    }

    public T setTurnMana(int turnMana) {
        this.turnMana = turnMana;
        return (T) this;
    }

    public int getManaAdd() {
        return manaAdd;
    }

    public T setManaAdd(int manaAdd) {
        this.manaAdd = manaAdd;
        return (T) this;
    }

    public T addManaAdd(int amt) {
        this.manaAdd += amt;
        return (T) this;
    }

    public int getQueuedManaRemoval() {
        return queuedManaRemoval;
    }

    public T setQueuedManaRemoval(int queuedManaRemoval) {
        this.queuedManaRemoval = queuedManaRemoval;
        return (T) this;
    }

    public T addQueuedManaRemoval(int amt) {
        this.queuedManaRemoval += amt;
        return (T) this;
    }

    public int getQueuedManaExtra() {
        return queuedManaExtra;
    }

    public T setQueuedManaExtra(int queuedManaExtra) {
        this.queuedManaExtra = queuedManaExtra;
        return (T) this;
    }

    public T addQueuedManaExtra(int amt) {
        this.queuedManaExtra += amt;
        return (T) this;
    }

    public int calculateDamage(ElementType element) {
        return attack * (1 + eAffinity.get(element) / 100) * (1 + (Math.random() <= critRate ? critDamage : 0));
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

    public void setDirection(Direction ns, Direction ew) {
        this.nsDir = ns;
        this.ewDir = ew;
    }

    public void setEWDirection(Direction ew) {
        this.ewDir = ew;
    }

    public void setNSDirection(Direction ns) {
        this.nsDir = ns;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getSpriteY() {
        return spriteY;
    }

    public void setSpriteY(int spriteY) {
        this.spriteY = spriteY;
    }
}
