package entities.units.enemy;

import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Arrays;

public class GoblinBoss extends Enemy{
    public GoblinBoss(float x, float y) throws SlickException {
        super(x, y, new ArrayList<Enemy>(Arrays.asList(new GoblinBoss(0, 0, new ArrayList<>()), new Goblin(0, 0, new ArrayList<>()), new Goblin(0, 0, new ArrayList<>()))));

    }

    public GoblinBoss(float x, float y, ArrayList<Enemy> enemies) throws SlickException {
        super(x, y, enemies);
        this.attack = 20;
        this.health = 500;
    }
}
