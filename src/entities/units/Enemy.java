package entities.units;

import entities.core.Coordinate;
import entities.core.Hitbox;
import entities.core.Team;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

enum  states    {
    ATTACK,CHARGE,SPECIAL,IDLE;
}

public class Enemy extends Unit {
    final int NUM_STATES = 4;
    states state;
    public Enemy(float x, float y) throws SlickException {//later change parameters to also change size, level, speed, and sprite
        this.width = 256;
        this.height = 128;
        this.position = new Coordinate(x, y);
        this.xSpeed = 10;
        this.ySpeed = 5;//y speed needs to be half of x speed so the movement has that isometric feel
        this.sheet = new SpriteSheet("res/experimentalEnemy.png", 256, 512);
        this.sprite = sheet.getSprite(0, 0);
        this.level = 1;
        state = states.IDLE;
        this.team = Team.ENEMY;
    }

    public void overworldUpdate()    {

    }

    public void battleMove(Unit target)    {
        if(state != states.CHARGE)  {
            this.state = decideState();
        }
        switch(state)   {
            case IDLE -> {
                break;
            }
            case ATTACK -> {
                target.takeDamage(this.attack);
            }
            case CHARGE -> {
                this.state = states.SPECIAL;
            }
            case SPECIAL -> {
                target.takeDamage(this.attack*3);
            }
        }
    }

    private states decideState()  {
        switch((int)(Math.random()*NUM_STATES)) {
            case 0:
                return states.IDLE;
            case 1:
                return states.CHARGE;
            default: return states.ATTACK;
        }
    }

    public Enemy getEnemy() {
        return this;
    }
}
