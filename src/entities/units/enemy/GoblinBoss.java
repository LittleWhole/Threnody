package entities.units.enemy;

import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Arrays;

public class GoblinBoss extends Enemy{
    public GoblinBoss(float x, float y) throws SlickException {
        super(x, y, new ArrayList<Enemy>(Arrays.asList(new GoblinBoss(0, 0, new ArrayList<>()), new Goblin(0, 0, new ArrayList<>(), 40), new Goblin(0, 0, new ArrayList<>(), 40))));

    }
    public GoblinBoss(float x, float y, ArrayList<Enemy> enemies, int level) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        super(x,y,enemies,level);

    }

    public GoblinBoss(float x, float y, ArrayList<Enemy> enemies) throws SlickException {
        super(x, y, enemies);
        this.level = 50;
        this.attack = 100;
        this.health = 500;
    }
}
