package entities.units.enemy;

import combat.artes.ElementType;
import entities.units.Unit;
import org.checkerframework.checker.units.qual.A;
import org.lwjgl.opengl.SGISGenerateMipmap;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Arrays;

public class Goblin<T extends Goblin<?>> extends Enemy<T> {
    public Goblin(float x, float y) throws SlickException {
        super(x, y, new ArrayList<Enemy>(Arrays.asList(new Goblin(0, 0, new ArrayList<>()), new Goblin(0, 0, new ArrayList<>()), new Goblin(0, 0, new ArrayList<>()))));

        this.attack = 9;
    }
    public Goblin(float x, float y, ArrayList<Enemy> enemies) throws SlickException {
        super(x, y, enemies);
        this.attack = 9;
    }

    @Override
    public void resetTeam() {
        try {
            enemyTeam = new ArrayList<Enemy>(Arrays.asList(new Goblin(0, 0, new ArrayList<>()), new Goblin(0, 0, new ArrayList<>()), new Goblin(0, 0, new ArrayList<>())));
        }
        catch (SlickException e)    {

        }
    }

    public void action(Unit target) {
        switch (turn) {
            case IDLE -> {
                break;
            }
            case ATTACK -> {
                target.takeDamage(this.attack, ElementType.PHYSICAL);
            }
        }
    }
}
