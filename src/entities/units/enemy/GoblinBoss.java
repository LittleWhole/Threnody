package entities.units.enemy;

import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Arrays;

public class GoblinBoss extends Enemy{
    final int healthScale = 100;
    final int atkScale = 50;
    final int defScale = 25;
    final float critRateScale = 0.1f;
    final int critScale = 20;
    public GoblinBoss(float x, float y) throws SlickException {

        super(x, y, new ArrayList<Enemy>(Arrays.asList(new GoblinBoss(0, 0, new ArrayList<>(), 50), new Goblin(0, 0, new ArrayList<>(), 40), new Goblin(0, 0, new ArrayList<>(), 40))));
        this.teamLvlMin = 40;
        this.teamLvlMax = 40;
    }
    public GoblinBoss(float x, float y, ArrayList<Enemy> enemies, int level) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        super(x,y,enemies,level);
    }

    public GoblinBoss(float x, float y, ArrayList<Enemy> enemies) throws SlickException {
        super(x, y, enemies);
        this.teamLvlMin = 50;
        this.teamLvlMax = 50;
        this.level = 50;
        this.attack = 5 + 5*(int)(Math.log(level+2));
        this.health = 500 + 100 * (int)(Math.log(level+2));
    }
}
