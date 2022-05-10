package entities.units;

import entities.core.Coordinate;
import entities.core.Entity;
import entities.core.EntityType;
import org.newdawn.slick.Graphics;

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

    public Unit() {
        super();
        this.type = EntityType.UNIT;
        this.level = 1;
        this.health = 1;
        this.attack = 1;
        this.defense = 1;
        this.critRate = 1;
        this.critDamage = 1;
        this.eAttack = 1;
        this.eDefense = 1;
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

    public void takeDamage(int amount)  {
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
}
