package entities.units.enemy;

import combat.artes.ElementType;
import entities.core.Coordinate;
import entities.core.Team;
import entities.units.Unit;
import org.checkerframework.checker.units.qual.A;
import org.lwjgl.opengl.SGISGenerateMipmap;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Goblin<T extends Goblin<?>> extends Enemy<T> {
    public Goblin(float x, float y) throws SlickException {
        super(x, y, new ArrayList<Enemy>(Arrays.asList(new Goblin(0, 0, new ArrayList<>()), new Goblin(0, 0, new ArrayList<>()), new Goblin(0, 0, new ArrayList<>()))));
        setEnemyTeamLevels(1,3);
        this.attack = 9;
    }
    public Goblin(float x, float y, ArrayList<Enemy> enemies) throws SlickException {
        super(x, y, enemies);
        setEnemyTeamLevels(1,3);
        this.attack = 9;
    }
    public Goblin(float x, float y, ArrayList<Enemy> enemies, int level) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        super(x,y,enemies,level);

    }

    @Override
    public void resetTeam() {
        int temp = this.enemyTeam.size();
        enemyTeam.clear();
        for(int i = 0; i < temp; i++)   {
            try {
                enemyTeam.add(new Goblin(0,0, new ArrayList<>(), (int)(Math.random()*teamLvlMax)+teamLvlMin));
            } catch (SlickException e) {
                throw new RuntimeException(e);
            }
        }
        //try {
            //enemyTeam = new ArrayList<Enemy>(Arrays.asList(new Goblin(0, 0, new ArrayList<>()), new Goblin(0, 0, new ArrayList<>()), new Goblin(0, 0, new ArrayList<>())));
        //}
        //catch (SlickException e)    {

        //}
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
